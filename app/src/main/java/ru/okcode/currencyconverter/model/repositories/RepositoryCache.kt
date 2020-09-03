package ru.okcode.currencyconverter.model.repositories

import androidx.lifecycle.LiveData
import ru.okcode.currencyconverter.model.Rates

interface RepositoryCache {
    val cachedRates: LiveData<Rates>

    suspend fun refreshCacheRates(immediately: Boolean = false)
}