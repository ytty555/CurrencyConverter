package ru.okcode.currencyconverter.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.okcode.currencyconverter.data.db.cache.CacheDao
import ru.okcode.currencyconverter.data.db.cache.CacheDatabase
import ru.okcode.currencyconverter.data.db.config.ConfigDao
import ru.okcode.currencyconverter.data.db.config.ConfigDatabase
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    // CacheRatesDatabase -------------------------------------------------------------------
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

    // ConfigDatabase -------------------------------------------------------------------
    @Provides
    @Singleton
    fun provideConfigDatabase(@ApplicationContext context: Context): ConfigDatabase {
        return Room.databaseBuilder(
            context,
            ConfigDatabase::class.java,
            "config"
        ).build()
    }

    @Provides
    @Singleton
    fun provideConfigDao(database: ConfigDatabase): ConfigDao {
        return database.configDao()
    }
}