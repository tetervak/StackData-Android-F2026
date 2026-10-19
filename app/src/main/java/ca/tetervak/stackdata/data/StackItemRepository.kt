package ca.tetervak.stackdata.data

import ca.tetervak.stackdata.model.StackEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


interface StackItemRepository {
    fun getStackItems(): Flow<List<StackEntry>>

    suspend fun push(value: String)

    suspend fun pop(): String
}

class RoomStackItemRepository(
    private val itemDao: ItemDao
) : StackItemRepository {

    override fun getStackItems(): Flow<List<StackEntry>> =
        itemDao.getAllItemsFlow().map { list ->
            list.mapIndexed { index, value ->
                StackEntry(list.size - index, value)
            }
        }

    override suspend fun push(value: String) = itemDao.insert(value)

    override suspend fun pop(): String = itemDao.deleteLastItem()
}