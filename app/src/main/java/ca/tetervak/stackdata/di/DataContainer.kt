package ca.tetervak.stackdata.di

import android.content.Context
import ca.tetervak.stackdata.data.ItemDatabase
import ca.tetervak.stackdata.data.StackItemRepository

class DataContainer(private val context: Context) {

    val stackItemRepository: StackItemRepository by lazy {
        StackItemRepository(ItemDatabase.getDatabase(context).itemDao)
    }
}