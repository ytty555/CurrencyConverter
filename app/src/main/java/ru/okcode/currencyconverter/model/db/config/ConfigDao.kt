package ru.okcode.currencyconverter.model.db.config

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ConfigDao {
    @Transaction
    @Query("SELECT * FROM ConfigEntity")
    fun getConfig(): LiveData<ConfigEntity?>
}