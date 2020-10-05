package ru.okcode.currencyconverter.data.repository

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.ui.overview.OverviewResult
import ru.okcode.currencyconverter.util.isActualByDate
import javax.inject.Inject

class RawRatesRepositoryImpl @Inject constructor(
    private val network: NetworkRepository,
    private val cache: CacheRepository
) : RawRatesRepository {

    override fun getRatesObservable(): Flowable<Rates> {
        return cache.getRatesObservable()
            .doOnNext{
                Log.e(">>> 2 >>>", "$it")
            }
    }

    override fun getRatesSingle(): Single<Rates> {
        return cache.getRatesSingle()
    }


    override fun updateRawRates(): Single<UpdateStatus> {
        return fetchCacheRatesSingle()
            .flatMap {
                Log.e("qq", "RawRatesRepositoryImpl updateRawRates() NotNeeded $it")
                Single.just(UpdateStatus.NotNeededToUpdate)
            }
            .cast(UpdateStatus::class.java)
            .onErrorResumeNext(
                fetchNetworkRatesSingle()
                    .flatMap {
                        Log.e("qq", "RawRatesRepositoryImpl updateRawRates() Success")
                        Single.just(UpdateStatus.Success)
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