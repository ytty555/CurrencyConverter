package ru.okcode.currencyconverter.model

import androidx.lifecycle.LiveData
import ru.okcode.currencyconverter.model.readyRates.Rates

interface RepositoryCache {
    val cachedRates: LiveData<Rates>

    suspend fun refreshCacheRates(immediately: Boolean = false)
}