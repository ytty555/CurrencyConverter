package ru.okcode.currencyconverter.data.repository

import io.reactivex.Flowable
import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.util.isActualByDate
import javax.inject.Inject

class RawRatesRepositoryImpl @Inject constructor(
    private val network: NetworkRepository,
    private val cache: CacheRepository
) : RawRatesRepository {

    override fun getRatesObservable(): Flowable<Rates> {
        return cache.getRatesObservable()
    }

    override fun getRatesSingle(): Single<Rates> {
        return cache.getRatesSingle()
    }


    override fun updateRawRates(nothingToUpdateMessageShow: Boolean): Single<UpdateStatus> {
        return fetchCacheRatesSingle()
            .flatMap {
                Single.just(UpdateStatus.NotNeededToUpdate(nothingToUpdateMessageShow, it))
            }
            .cast(UpdateStatus::class.java)
            .onErrorResumeNext(
                fetchNetworkRatesSingle()
                    .flatMap {
                        Single.just(UpdateStatus.Success(it))
                    }
            )
    }

    private fun fetchNetworkRatesSingle(): Single<Rates> {
        return network.getRatesSingle()
            .flatMap { rates ->
                cache
                    .saveCache(rates)
                    .andThen(Single.just(rates))
            }
    }

    private fun fetchCacheRatesSingle(): Single<Rates> {
        return cache.getRatesSingle()
            .flatMap { rates ->
                if (isActualByDate(rates.timeLastUpdateUnix, rates.timeNextUpdateUnix)) {
                    Single.just(rates)
                } else {
                    Single.error(Throwable("Cache has old data"))
                }
            }
    }
}