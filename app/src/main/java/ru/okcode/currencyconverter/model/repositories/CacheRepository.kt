package ru.okcode.currencyconverter.model.repositories

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred
import ru.okcode.currencyconverter.model.Rates
import ru.okcode.currencyconverter.model.db.cache.CacheHeaderWithRates

interface CacheRepository {
    val cacheDataSource: LiveData<Rates>

    val apiStatusDataSource: LiveData<Status>

    suspend fun refreshCacheRates(immediately: Boolean = false)

    fun getCacheRatesAsync(): Deferred<Rates?>
}

enum class Status {
    LOADING,
    DONE,
    ERROR
}