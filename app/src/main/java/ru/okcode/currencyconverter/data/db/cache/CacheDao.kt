package ru.okcode.currencyconverter.data.db.cache

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface CacheDao {
    @Transaction
    @Query("SELECT * FROM CacheRatesHeader")
    fun getCacheFlowable(): Flowable<CacheHeaderWithRates>

    @Transaction
    @Query("SELECT * FROM CacheRatesHeader")
    fun getCacheSingle(): Single<CacheHeaderWithRates>

    @Transaction
    fun insertToCache(cacheRatesHeaderWithRates: CacheHeaderWithRates) {
        clearCacheHeader()
        clearCacheCurrencyRates()
        insertCacheHeader(cacheRatesHeaderWithRates.cacheHeader)
        insertCacheRates(cacheRatesHeaderWithRates.rates)

    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCacheHeader(cache: CacheRatesHeader)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCacheRates(ratesList: List<CacheCurrencyRate>)

    @Query("DELETE FROM CacheRatesHeader")
    fun clearCacheHeader()

    @Query("DELETE FROM CacheCurrencyRate")
    fun clearCacheCurrencyRates()


}