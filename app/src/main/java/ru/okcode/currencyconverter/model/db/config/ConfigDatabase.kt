package ru.okcode.currencyconverter.model.db.config

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ConfigEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ConfigDatabase : RoomDatabase() {
    abstract fun configDao(): ConfigDao
}