package ru.okcode.currencyconverter.model.dbWork

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [WorkRatesSet::class, WorkRatesItem::class],
    version = 1,
    exportSchema = false
)
abstract class WorkDataBase: RoomDatabase() {

    abstract val workRatesDao: WorkRatesDao

    companion object {
        @Volatile
        private var INSTANCE: WorkDataBase? = null

        fun getInstance(context: Context): WorkDataBase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WorkDataBase::class.java,
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