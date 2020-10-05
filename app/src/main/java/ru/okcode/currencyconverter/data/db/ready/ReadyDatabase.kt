package ru.okcode.currencyconverter.data.db.ready

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ReadyHeader::class, ReadyRate::class],
    version = 1
)
abstract class ReadyDatabase : RoomDatabase() {
    abstract fun readyDao(): ReadyDao
}