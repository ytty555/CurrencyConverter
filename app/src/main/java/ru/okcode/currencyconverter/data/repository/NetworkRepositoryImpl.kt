package ru.okcode.currencyconverter.data.repository

import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.data.network.ApiService
import ru.okcode.currencyconverter.data.network.RatesDtoToRatesMapper
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val ratesDtoToRatesMapper: RatesDtoToRatesMapper
) : NetworkRepository {


    override fun getRates(): Single<Rates> {
        return api.getRates()
            .flatMap { ratesDto ->
                val rates = ratesDtoToRatesMapper.mapToModel(ratesDto)!!
                Single.just(rates)
            }

//        return Single.create { emitter ->
//            val disposable = api.getRates()
//                .subscribeBy(
//                    onSuccess = {
//                        val rates = ratesDtoToRatesMapper.mapToModel(it)!!
//                        Log.e("qq", "NetworkRepositoryImpl getRates() onSuccess $rates" )
//                        emitter.onSuccess(rates)
//                    },
//                    onError = {
//                        emitter.onError(it)
//                    }
//                )
//            disposables.add(disposable)
    }
}