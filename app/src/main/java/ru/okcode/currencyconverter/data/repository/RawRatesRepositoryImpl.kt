package ru.okcode.currencyconverter.data.repository

import io.reactivex.Observable
import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.util.isActualByDate
import javax.inject.Inject

class RawRatesRepositoryImpl @Inject constructor(
    private val network: NetworkRepository,
    private val cache: CacheRepository
) : RawRatesRepository {

    override fun getRates(): Observable<Rates> {

        val networkDataSource = network.getRates()

        val cacheDataSource = cache.getRates()

        return Single.merge(
            cacheDataSource
                .filter {
                    isActualByDate(it.timeLastUpdateUnix, it.timeNextUpdateUnix)
                }
                .toSingle(),
            networkDataSource
                .flatMap {
                    cache
                        .saveCache(it)
                        .andThen(Single.just(it))
                }
        )
            .toObservable()
    }
}