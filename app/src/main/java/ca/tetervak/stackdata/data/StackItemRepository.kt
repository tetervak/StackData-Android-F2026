package ca.tetervak.stackdata.data

import ca.tetervak.stackdata.model.StackEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StackItemRepository(
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