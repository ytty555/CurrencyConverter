package ru.okcode.currencyconverter.model.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Cache::class, CurrencyRate::class],
    version = 1,
    exportSchema = false
)
abstract class CacheDatabase: RoomDatabase() {
    abstract fun cacheDao(): CacheDao
}