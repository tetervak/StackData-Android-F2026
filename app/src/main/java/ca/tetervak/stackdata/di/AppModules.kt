package ca.tetervak.stackdata.di

import androidx.room.Room
import ca.tetervak.stackdata.data.ItemDao
import ca.tetervak.stackdata.data.ItemDatabase
import ca.tetervak.stackdata.data.RoomStackItemRepository
import ca.tetervak.stackdata.data.StackItemRepository
import ca.tetervak.stackdata.ui.stack.StackViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single<ItemDatabase> {
        Room.databaseBuilder(androidContext(), ItemDatabase::class.java, "item_database")
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    single<ItemDao> { get<ItemDatabase>().itemDao }
}

val repositoryModule = module {
    single<StackItemRepository>{ RoomStackItemRepository(itemDao = get()) }
}

val viewModelModule = module {
    viewModel<StackViewModel> { StackViewModel(repository = get()) }
}

val appModules = listOf(databaseModule, repositoryModule, viewModelModule)
