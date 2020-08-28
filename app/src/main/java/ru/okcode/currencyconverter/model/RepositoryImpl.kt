package ru.okcode.currencyconverter.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.okcode.currencyconverter.model.api.ApiService
import ru.okcode.currencyconverter.model.api.RatesDto
import ru.okcode.currencyconverter.model.api.toCacheCurrencyRatesList
import ru.okcode.currencyconverter.model.api.toCacheRatesHeader
import ru.okcode.currencyconverter.model.db.CacheDao
import ru.okcode.currencyconverter.model.db.toDomainModel
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val cacheDao: CacheDao
) : Repository {

    override val rates: LiveData<Rates> =
        Transformations.map(cacheDao.getCacheRates()) { cacheHeaderWithRates ->
            cacheHeaderWithRates.toDomainModel()
        }

    suspend fun refreshCacheRates() {
        withContext(Dispatchers.IO) {
            val apiRates: RatesDto = api.getRatesAsync().await()
            cacheDao.insertToCache(
                apiRates.toCacheRatesHeader(),
                apiRates.toCacheCurrencyRatesList()
            )
        }
    }
}