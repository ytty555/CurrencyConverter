package ru.okcode.currencyconverter.model

import android.util.Log
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

    override val cachedRates: LiveData<Rates> =
        Transformations.map(cacheDao.getCacheRates()) { cacheHeaderWithRates ->
            Log.e("qq", "RepositoryImpl: Setting rates LiveData...")
            cacheHeaderWithRates?.toDomainModel()
        }

    override suspend fun refreshCacheRates(immediately: Boolean) {
        val isActualDataInCache = cacheDao.isActual().await()
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

}