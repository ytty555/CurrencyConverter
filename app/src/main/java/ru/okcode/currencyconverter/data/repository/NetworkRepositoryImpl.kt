package ru.okcode.currencyconverter.data.repository

import io.reactivex.Observable
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.data.network.ApiService
import ru.okcode.currencyconverter.data.network.RatesDtoToRatesMapper
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val ratesDtoToRatesMapper: RatesDtoToRatesMapper
) : NetworkRepository {

    override fun getRates(): Observable<Rates> {
        return api.getRates()
            .flatMap { ratesDto ->
                val rates = ratesDtoToRatesMapper.mapToModel(ratesDto)!!
                Observable.just(rates)
            }
    }
}