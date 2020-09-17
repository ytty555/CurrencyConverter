package ru.okcode.currencyconverter.data.db.cache

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.util.*

@Dao
interface CacheDao {
    @Transaction
    @Query("SELECT * FROM CacheRatesHeader")
    fun getCacheRatesDataSource(): LiveData<CacheHeaderWithRates?>

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

    //  -------------------------------------
    @Transaction
    @Query("SELECT * FROM CacheRatesHeader")
    fun getCacheRates(): CacheHeaderWithRates

    // Cache has an actual data checking -------------------------------------
    @Transaction
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