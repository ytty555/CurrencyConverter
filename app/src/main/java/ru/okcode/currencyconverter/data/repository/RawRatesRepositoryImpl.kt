package ru.okcode.currencyconverter.data.repository

import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.util.isActualByDate
import javax.inject.Inject

class RawRatesRepositoryImpl @Inject constructor(
    private val network: NetworkRepository,
    private val cache: CacheRepository
) : RawRatesRepository {

    override fun getRates(): Single<Rates> {
        return fetchCacheRates()
            .onErrorResumeNext(fetchNetworkRates())
    }

    private fun fetchNetworkRates(): Single<Rates> {
        return network.getRates()
            .flatMap { rates ->
                cache
                    .saveCache(rates)
                    .andThen(Single.just(rates))
            }
    }

    private fun fetchCacheRates(): Single<Rates> {
        return cache.getRates()
            .flatMap { rates ->
                if (isActualByDate(rates.timeLastUpdateUnix, rates.timeNextUpdateUnix)) {
                    Single.just(rates)
                } else {
                    Single.error(Throwable("Cache has old data"))
                }
            }
    }
}