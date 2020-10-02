package ru.okcode.currencyconverter.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.util.isActualByDate
import javax.inject.Inject

class RawRatesRepositoryImpl @Inject constructor(
    private val network: NetworkRepository,
    private val cache: CacheRepository
) : RawRatesRepository {

    override fun getRatesObservable(): Observable<Rates> {
        return fetchCacheRates()
            .onErrorResumeNext(fetchNetworkRates())
    }

    private fun fetchNetworkRates(): Observable<Rates> {
        return network.getRates()
            .flatMap { rates ->
                cache
                    .saveCache(rates)
                    .andThen(Observable.just(rates))
            }
    }

    private fun fetchCacheRates(): Observable<Rates> {
        return cache.getRates()
            .flatMap { rates ->
                if (isActualByDate(rates.timeLastUpdateUnix, rates.timeNextUpdateUnix)) {
                    Observable.just(rates)
                } else {
                    Observable.error(Throwable("Cache has old data"))
                }
            }
    }
}