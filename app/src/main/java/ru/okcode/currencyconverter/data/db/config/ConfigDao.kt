package ru.okcode.currencyconverter.data.db.config

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ConfigDao {

    @Transaction
    @Query("SELECT * FROM ConfigHeader")
    fun getConfig(): Flowable<ConfigHeaderWithCurrencies>

    @Transaction
    @Query("SELECT * FROM ConfigHeader")
    fun getConfigSingle(): Single<ConfigHeaderWithCurrencies>

    @Transaction
    fun insertConfig(config: ConfigHeaderWithCurrencies) {
        clearHeader()
        clearCurrencies()
        insertConfigHeader(config.configHeader)
        for (currency in config.currencies) {
            insertConfigCurrency(currency)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConfigHeader(configHeader: ConfigHeader)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConfigCurrency(configCurrency: ConfigCurrency)

    @Query("DELETE FROM ConfigHeader")
    fun clearHeader()

    @Query("DELETE FROM ConfigCurrency")
    fun clearCurrencies()
}