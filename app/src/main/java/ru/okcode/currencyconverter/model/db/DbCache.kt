package ru.okcode.currencyconverter.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.okcode.currencyconverter.model.Converters

@Database(
    entities = [DataSetEntity::class, RateEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DbCache : RoomDatabase() {

    abstract val cacheRatesDao: RatesDao

    companion object {
        @Volatile
        private var INSTANCE: DbCache? = null

        fun getInstance(context: Context): DbCache {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DbCache::class.java,
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