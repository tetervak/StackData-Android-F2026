package ca.tetervak.stackdata.di

import android.content.Context
import androidx.room.Room
import ca.tetervak.stackdata.data.ItemDao
import ca.tetervak.stackdata.data.ItemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideItemDao(db: ItemDatabase): ItemDao {
        return db.itemDao
    }

    @Singleton
    @Provides
    fun provideItemDatabase(@ApplicationContext context: Context): ItemDatabase {
        return Room.databaseBuilder(context, ItemDatabase::class.java, "item_database")
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }
}