package ru.okcode.currencyconverter.model.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.*
import ru.okcode.currencyconverter.model.Rates
import ru.okcode.currencyconverter.model.db.cache.CacheDao
import ru.okcode.currencyconverter.model.db.cache.CacheMapper
import ru.okcode.currencyconverter.model.network.ApiService
import ru.okcode.currencyconverter.model.network.RatesDto
import ru.okcode.currencyconverter.model.network.toCacheCurrencyRatesList
import ru.okcode.currencyconverter.model.network.toCacheRatesHeader
import javax.inject.Inject

class CacheRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val cacheDao: CacheDao,
    private val cacheMapper: CacheMapper
) : CacheRepository {

    override val cacheDataSource: LiveData<Rates> =
        Transformations.map(cacheDao.getCacheRatesDataSource()) { cacheHeaderWithRates ->
            cacheHeaderWithRates?.let {
                cacheMapper.mapToModel(it)
            }
        }

    override suspend fun refreshCacheRates(immediately: Boolean) {
        val isActualDataInCache = cacheDao.isActualAsync().await()

        if (!immediately && isActualDataInCache) {
            return
        }

        withContext(Dispatchers.IO) {
            try {
                val apiRates: RatesDto = api.getRatesAsync().await()
                cacheDao.insertToCache(
                    apiRates.toCacheRatesHeader(),
                    apiRates.toCacheCurrencyRatesList()
                )

            } catch (e: Exception) {

            }
        }
    }

    override fun getCacheRatesAsync(): Deferred<Rates?> {
       return GlobalScope.async {
           cacheMapper.mapToModel(cacheDao.getCacheRates())
       }
    }

}