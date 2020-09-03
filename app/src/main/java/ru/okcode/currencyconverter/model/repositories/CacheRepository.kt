package ru.okcode.currencyconverter.model.repositories

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred
import ru.okcode.currencyconverter.model.Rates

interface CacheRepository {
    val cacheDataSource: LiveData<Rates>

    suspend fun refreshCacheRates(immediately: Boolean = false)
}