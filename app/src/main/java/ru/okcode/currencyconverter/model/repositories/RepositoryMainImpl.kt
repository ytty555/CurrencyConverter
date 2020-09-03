package ru.okcode.currencyconverter.model.repositories

import androidx.lifecycle.LiveData
import javax.inject.Inject

class RepositoryMainImpl @Inject constructor(
    private val repositoryCache: RepositoryCache,
    private val repositoryConfig: RepositoryConfig
) : RepositoryMain {

    override val baseCurrencyCode: LiveData<String> = repositoryConfig.baseCurrencyCode

    override val rawRates = repositoryCache.cachedRates

    override suspend fun refreshData(immediately: Boolean) {
        repositoryCache.refreshCacheRates(immediately)
    }

    override suspend fun updateBaseCurrencyCode(code: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateBaseCurrencyAmount(amount: Double) {
        TODO("Not yet implemented")
    }
}