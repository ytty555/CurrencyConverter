package ru.okcode.currencyconverter.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.*
import java.util.*

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

    // Cache has an actual data checking -------------------------------------
    @Query("SELECT * FROM CacheRatesHeader")
    fun getDataForCheckCache(): CacheHeaderWithRates?

    fun isActual(): Deferred<Boolean> {
        return GlobalScope.async(Dispatchers.IO) {
            val cacheData = getDataForCheckCache()
            val currentTimeStamp = Date().time / 1000
            cacheData != null
                    && currentTimeStamp >= cacheData.cache.timeLastUpdateUnix
                    && currentTimeStamp < cacheData.cache.timeNextUpdateUnix
                    && !cacheData.rates.isNullOrEmpty()
        }
    }
}