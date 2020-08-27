package ru.okcode.currencyconverter.model.db

import androidx.room.*

@Dao
interface CacheDao {
    @Transaction
    @Query("SELECT * FROM Cache")
    fun getCacheRates(): List<CacheRates>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCacheRates(cache: Cache, currencyRate: CurrencyRate)

    @Delete
    fun deleteAll(vararg cache: Cache)
}