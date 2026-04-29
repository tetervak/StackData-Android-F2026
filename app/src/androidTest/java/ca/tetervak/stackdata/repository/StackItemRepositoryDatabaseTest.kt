package ca.tetervak.stackdata.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import ca.tetervak.stackdata.data.ItemDao
import ca.tetervak.stackdata.data.ItemDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class StackItemRepositoryDatabaseTest {

    private lateinit var itemDao: ItemDao
    private lateinit var db: ItemDatabase
    private lateinit var repository: StackItemRepository

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ItemDatabase::class.java
        ).build()
        itemDao = db.itemDao
        repository = StackItemRepository(itemDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun pushAndGetItems() = runBlocking {
        repository.push("Hello")
        repository.push("World")
        
        val items = repository.getStackItems().first()
        
        assertEquals(2, items.size)
        // Order is DESC by id, so "World" should be first (top of stack)
        assertEquals("World", items[0].value)
        assertEquals(2, items[0].count)
        
        assertEquals("Hello", items[1].value)
        assertEquals(1, items[1].count)
    }

    @Test
    fun popItem() = runBlocking {
        repository.push("A")
        repository.push("B")
        
        val popped = repository.pop()
        assertEquals("B", popped)
        
        val items = repository.getStackItems().first()
        assertEquals(1, items.size)
        assertEquals("A", items[0].value)
    }

    @Test
    fun pushManyItems_checkOrderAndCounts() = runBlocking {
        val testData = listOf("One", "Two", "Three", "Four", "Five")
        for (item in testData) {
            repository.push(item)
        }
        
        val items = repository.getStackItems().first()
        assertEquals(5, items.size)
        
        // Stack should be reversed order: Five, Four, Three, Two, One
        // Counts should be 5, 4, 3, 2, 1
        for (i in items.indices) {
            assertEquals(testData[4 - i], items[i].value)
            assertEquals(5 - i, items[i].count)
        }
    }
}
