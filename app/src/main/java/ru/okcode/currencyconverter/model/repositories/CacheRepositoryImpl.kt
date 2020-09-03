package ru.okcode.currencyconverter.model.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.*
import ru.okcode.currencyconverter.model.Rates
import ru.okcode.currencyconverter.model.db.cache.CacheDao
import ru.okcode.currencyconverter.model.db.cache.CacheHeaderWithRates
import ru.okcode.currencyconverter.model.network.ApiService
import ru.okcode.currencyconverter.model.network.RatesDto
import ru.okcode.currencyconverter.model.network.toCacheCurrencyRatesList
import ru.okcode.currencyconverter.model.network.toCacheRatesHeader
import javax.inject.Inject

class CacheRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val cacheDao: CacheDao,
) : CacheRepository {

    override val cacheDataSource: LiveData<Rates> =
        Transformations.map(cacheDao.getCacheRates()) { cacheHeaderWithRates ->
            Log.e("qq", "RepositoryImpl: Setting rates LiveData...")
            cacheHeaderWithRates?.toModel()
        }

    override suspend fun refreshCacheRates(immediately: Boolean) {
        val isActualDataInCache = cacheDao.isActualAsync().await()
        Log.e("qq", "RepositoryImpl: Cache actual: $isActualDataInCache")
        if (!immediately && isActualDataInCache) {
            Log.e("qq", "RepositoryImpl: Cache has actual data. Do nothing")
            return
        }

        Log.e("qq", "RepositoryImpl: Starting cache refreshing")
        withContext(Dispatchers.IO) {
            try {
                val apiRates: RatesDto = api.getRatesAsync().await()
                cacheDao.insertToCache(
                    apiRates.toCacheRatesHeader(),
                    apiRates.toCacheCurrencyRatesList()
                )
                Log.e("qq", "RepositoryImpl: Data cached!")

            } catch (e: Exception) {
                Log.e("qq", "RepositoryImpl: Error: ${e.message}")

            }
        }
    }

    override suspend fun getCachedRatesAsync(): Deferred<Rates> {
        refreshCacheRates()
        val cacheHeaderWithRates: CacheHeaderWithRates = cacheDao.getCacheRatesAsync().await()

        return GlobalScope.async {
            cacheHeaderWithRates.toModel()
        }
    }
}