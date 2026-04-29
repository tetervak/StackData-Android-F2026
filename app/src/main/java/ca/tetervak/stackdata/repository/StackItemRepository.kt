package ca.tetervak.stackdata.repository

import ca.tetervak.stackdata.data.ItemDao
import ca.tetervak.stackdata.domain.StackEntry
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

@Singleton
class StackItemRepository @Inject constructor(
    private val itemDao: ItemDao
){

    fun getStackItems(): Flow<List<StackEntry>> =
        itemDao.getAllItemsFlow().map { list ->
            list.mapIndexed { index, value ->
                StackEntry(list.size - index, value)
            }
        }

    suspend fun push(value: String) = itemDao.insert(value)

    suspend fun pop(): String = itemDao.deleteLastItem()
}