package ru.okcode.currencyconverter.data.db.config

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@Dao
interface ConfigDao {

    @Query("SELECT * FROM ConfigEntity")
    fun getConfig(): Single<ConfigEntity>

    @Insert
    fun insertConfig(config: ConfigEntity)

    @Query("DELETE FROM ConfigEntity")
    fun clear()
}