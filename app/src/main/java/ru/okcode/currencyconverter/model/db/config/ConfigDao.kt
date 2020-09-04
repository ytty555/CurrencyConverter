package ru.okcode.currencyconverter.model.db.config

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.*

private const val TAG = "ConfigDao"
@Dao
interface ConfigDao {

    @Query("SELECT * FROM ConfigEntity")
    fun getConfig(): LiveData<ConfigEntity?>

    @Query("SELECT * FROM ConfigEntity")
    suspend fun getConfigForNextUpdate(): ConfigEntity

    @Insert
    fun insertConfig(config: ConfigEntity)

    @Query("DELETE FROM ConfigEntity")
    fun clear()

    @Transaction
    suspend fun updateBaseCurrency(baseCurrencyCode: String) {
        val config = getConfigAsync().await()

        if (config.baseCurrencyCode == baseCurrencyCode) {
            return
        } else {
            config.baseCurrencyCode = baseCurrencyCode
        }

        clear()

        insertConfig(config)
        Log.e(TAG, "new Config = $config")
    }

    fun getConfigAsync(): Deferred<ConfigEntity> {
        return GlobalScope.async {
            getConfigForNextUpdate()
        }
    }
}