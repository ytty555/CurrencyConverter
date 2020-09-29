package ru.okcode.currencyconverter.data.db.cache

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface CacheDao {
    @Transaction
    @Query("SELECT * FROM CacheRatesHeader")
    fun getCache(): Maybe<CacheHeaderWithRates>

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
    fun clearCacheHeader(): Completable

    @Query("DELETE FROM CacheCurrencyRate")
    fun clearCacheCurrencyRates(): Completable


}