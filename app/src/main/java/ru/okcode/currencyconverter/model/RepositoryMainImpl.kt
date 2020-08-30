package ru.okcode.currencyconverter.model

import androidx.lifecycle.LiveData
import ru.okcode.currencyconverter.model.readyRates.Rates
import javax.inject.Inject

class RepositoryMainImpl @Inject constructor(
    private val repositoryCache: RepositoryCache
) : RepositoryMain {
    // TODO FIX IT
    override val readyRates: LiveData<Rates> = repositoryCache.cachedRates

    override suspend fun refreshData(immidiately: Boolean) {
        repositoryCache.refreshCacheRates(immidiately)
    }
}