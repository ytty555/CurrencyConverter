package ru.okcode.currencyconverter.model.db.config

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.okcode.currencyconverter.model.db.Converters

@Database(
    entities = [ConfigEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ConfigDatabase : RoomDatabase() {
    abstract fun configDao(): ConfigDao
}