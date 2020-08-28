package ru.okcode.currencyconverter.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [CacheRatesHeader::class, CacheCurrencyRate::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CacheDatabase: RoomDatabase() {
    abstract fun cacheDao(): CacheDao
}