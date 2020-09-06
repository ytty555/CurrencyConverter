package ru.okcode.currencyconverter.model.db.config

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@Dao
interface ConfigDao {

    @Query("SELECT * FROM ConfigEntity")
    fun getConfigDataSource(): LiveData<ConfigEntity?>

    @Query("SELECT * FROM ConfigEntity")
    suspend fun getConfig(): ConfigEntity

    @Insert
    fun insertConfig(config: ConfigEntity)

    @Query("DELETE FROM ConfigEntity")
    fun clear()

    @Transaction
    suspend fun updateBaseCurrency(baseCurrencyCode: String, amount: Float) {
        val configEntity = getConfigEntityAsync().await()

        if (configEntity.baseCurrencyCode == baseCurrencyCode
            && configEntity.baseCurrencyAmount == amount
        ) {
            return
        } else {
            configEntity.baseCurrencyCode = baseCurrencyCode
            configEntity.baseCurrencyAmount = amount
        }

        clear()

        insertConfig(configEntity)
    }

    fun getConfigEntityAsync(): Deferred<ConfigEntity> {
        return GlobalScope.async {
            getConfig()
        }
    }
}