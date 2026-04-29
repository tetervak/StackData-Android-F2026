package ca.tetervak.stackdata.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [StackItem::class], version = 1, exportSchema = false
)
abstract class ItemDatabase: RoomDatabase() {
    abstract val itemDao: ItemDao
}