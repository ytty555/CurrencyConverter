package ru.okcode.currencyconverter.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.*
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.data.db.cache.CacheDao
import ru.okcode.currencyconverter.data.db.cache.CacheMapper
import ru.okcode.currencyconverter.data.network.ApiService
import ru.okcode.currencyconverter.data.network.RatesDto
import ru.okcode.currencyconverter.data.network.toCacheCurrencyRatesList
import ru.okcode.currencyconverter.data.network.toCacheRatesHeader
import ru.okcode.currencyconverter.util.LOADING_TIMEOUT
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

    private var _apiStatusDataSource = MutableLiveData<Status>()
    override val apiStatusDataSource: LiveData<Status>
        get() = _apiStatusDataSource

    init {
        _apiStatusDataSource.value = Status.DONE
    }

    override suspend fun refreshCacheRates(immediately: Boolean) {
        val isActualDataInCache = cacheDao.isActualAsync().await()

        if (!immediately && isActualDataInCache) {
            return
        }

        _apiStatusDataSource.value = Status.LOADING

        withContext(Dispatchers.IO) {
            try {
                val apiRates: RatesDto? =withTimeoutOrNull(LOADING_TIMEOUT) {
                     api.getRatesAsync().await()
                }

                if (apiRates != null) {
                    cacheDao.insertToCache(
                        apiRates.toCacheRatesHeader(),
                        apiRates.toCacheCurrencyRatesList()
                    )
                    _apiStatusDataSource.postValue(Status.DONE)
                } else {
                    _apiStatusDataSource.postValue(Status.ERROR)
                }

            } catch (e: Exception) {
                _apiStatusDataSource.postValue(Status.ERROR)
            }
        }
    }

    override fun getCacheRatesAsync(): Deferred<Rates?> {
        return GlobalScope.async {
            cacheMapper.mapToModel(cacheDao.getCacheRates())
        }
    }

}