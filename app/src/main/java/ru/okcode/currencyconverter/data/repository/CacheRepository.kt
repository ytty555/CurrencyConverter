package ru.okcode.currencyconverter.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred
import ru.okcode.currencyconverter.data.model.Rates

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