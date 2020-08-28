package ru.okcode.currencyconverter.model.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CacheDao {
    @Transaction
    @Query("SELECT * FROM CacheRatesHeader")
    fun getCacheRates(): LiveData<CacheHeaderWithRates>

    @Transaction
    fun insertToCache(cacheRatesHeader: CacheRatesHeader, ratesList: List<CacheCurrencyRate>) {
        clearCacheTitle()
        clearCacheCurrencyRates()
        insertCacheTitle(cacheRatesHeader)
        insertCacheRates(ratesList)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCacheTitle(cache: CacheRatesHeader)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCacheRates(ratesList: List<CacheCurrencyRate>)

    @Query("DELETE FROM CacheRatesHeader")
    fun clearCacheTitle()

    @Query("DELETE FROM CacheCurrencyRate")
    fun clearCacheCurrencyRates()

}