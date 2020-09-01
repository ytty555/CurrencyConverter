package ru.okcode.currencyconverter.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.okcode.currencyconverter.model.readyRates.BaseCurrencyCodeChanger
import ru.okcode.currencyconverter.model.readyRates.Rates
import ru.okcode.currencyconverter.model.readyRates.ReadyRatesController
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