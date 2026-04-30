package ca.tetervak.stackdata.di

import androidx.room.Room
import ca.tetervak.stackdata.data.ItemDatabase
import ca.tetervak.stackdata.repository.StackItemRepository
import ca.tetervak.stackdata.ui.stack.StackViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dataModule = module {
    single {
        Room.databaseBuilder(androidContext(), ItemDatabase::class.java, "item_database")
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    single { get<ItemDatabase>().itemDao }

    singleOf(::StackItemRepository)

    viewModelOf(::StackViewModel)
}