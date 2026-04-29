package ca.tetervak.stackdata.di

import androidx.room.Room
import ca.tetervak.stackdata.data.ItemDao
import ca.tetervak.stackdata.data.ItemDatabase
import ca.tetervak.stackdata.repository.StackItemRepository
import ca.tetervak.stackdata.ui.stack.StackViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single<StackItemRepository> { StackItemRepository(get<ItemDao>()) }
    single<ItemDatabase> {
        Room.databaseBuilder(androidContext(), ItemDatabase::class.java, "item_database")
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }
    single<ItemDao> { get<ItemDatabase>().itemDao }
    viewModel<StackViewModel> { StackViewModel(get<StackItemRepository>()) }
}