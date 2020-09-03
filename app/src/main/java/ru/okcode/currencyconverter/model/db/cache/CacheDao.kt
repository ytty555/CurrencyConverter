package ru.okcode.currencyconverter.model.db.cache

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.*
import java.util.*

@Dao
interface CacheDao {
    @Transaction
    @Query("SELECT * FROM CacheRatesHeader")
    fun getCacheRates(): LiveData<CacheHeaderWithRates>

    @Query("SELECT * FROM CacheRatesHeader")
    fun getCacheRatesAsync(): Deferred<CacheHeaderWithRates>

    @Transaction
    fun insertToCache(cacheRatesHeader: CacheRatesHeader, ratesList: List<CacheCurrencyRate>) {
        clearCacheHeader()
        clearCacheCurrencyRates()
        insertCacheHeader(cacheRatesHeader)
        insertCacheRates(ratesList)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCacheHeader(cache: CacheRatesHeader)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCacheRates(ratesList: List<CacheCurrencyRate>)

    @Query("DELETE FROM CacheRatesHeader")
    fun clearCacheHeader()

    @Query("DELETE FROM CacheCurrencyRate")
    fun clearCacheCurrencyRates()

    // Cache has an actual data checking -------------------------------------
    @Query("SELECT * FROM CacheRatesHeader")
    fun getDataForCheckCache(): CacheHeaderWithRates?

    fun isActualAsync(): Deferred<Boolean> {
        return GlobalScope.async(Dispatchers.IO) {
            val cacheData = getDataForCheckCache()
            val currentTimeStamp = Date().time / 1000
            cacheData != null
                    && currentTimeStamp >= cacheData.cacheHeader.timeLastUpdateUnix
                    && currentTimeStamp < cacheData.cacheHeader.timeNextUpdateUnix
                    && !cacheData.rates.isNullOrEmpty()
        }
    }
}