package ru.okcode.currencyconverter.data.repository

import io.reactivex.Observable
import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.data.network.ApiService
import ru.okcode.currencyconverter.data.network.RatesDtoToRatesMapper
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val ratesDtoToRatesMapper: RatesDtoToRatesMapper
) : NetworkRepository {

    override fun getRatesObservable(): Observable<Rates> {
        return api.getRatesObservable()
            .map { ratesDto ->
                ratesDtoToRatesMapper.mapToModel(ratesDto)!!
            }
    }

    override fun getRatesSingle(): Single<Rates> {
        return api.getRatesSingle()
            .flatMap { ratesDto ->
                val rates = ratesDtoToRatesMapper.mapToModel(ratesDto)!!
                Single.just(rates)
            }
            .timeout(5, TimeUnit.SECONDS)
    }
}