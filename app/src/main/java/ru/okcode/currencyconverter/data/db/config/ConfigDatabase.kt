package ru.okcode.currencyconverter.data.db.config

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.okcode.currencyconverter.data.db.Converters

@Database(
    entities = [ConfigHeader::class, ConfigCurrency::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ConfigDatabase : RoomDatabase() {
    abstract fun configDao(): ConfigDao
}