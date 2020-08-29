package ru.okcode.currencyconverter.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.okcode.currencyconverter.model.db.CacheDao
import ru.okcode.currencyconverter.model.db.CacheDatabase
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCacheDatabase(@ApplicationContext context: Context): CacheDatabase {
        return Room.databaseBuilder(
            context,
            CacheDatabase::class.java,
            "cache_rates"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCacheDao(database: CacheDatabase): CacheDao {
        return database.cacheDao()
    }
}