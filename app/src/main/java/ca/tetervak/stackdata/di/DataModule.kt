package ca.tetervak.stackdata.di

import androidx.room.Room
import ca.tetervak.stackdata.data.ItemDao
import ca.tetervak.stackdata.data.ItemDatabase
import ca.tetervak.stackdata.data.RoomStackItemRepository
import ca.tetervak.stackdata.data.StackItemRepository
import ca.tetervak.stackdata.ui.stack.StackViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


val dataModule = module {
    single {
        Room.databaseBuilder(androidContext(), ItemDatabase::class.java, "item_database")
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    single<ItemDao> { get<ItemDatabase>().itemDao }

    singleOf(::RoomStackItemRepository) bind StackItemRepository::class

    viewModelOf(::StackViewModel)
}