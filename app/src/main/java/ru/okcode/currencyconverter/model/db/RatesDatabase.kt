package ru.okcode.currencyconverter.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [OperationEntity::class, RateEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RatesDatabase : RoomDatabase() {

    abstract val ratesDao: RatesDao

    companion object {
        @Volatile
        private var INSTANCE: RatesDatabase? = null

        fun getInstance(context: Context): RatesDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RatesDatabase::class.java,
                        "rates_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}