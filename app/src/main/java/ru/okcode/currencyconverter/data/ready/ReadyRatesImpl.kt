package ru.okcode.currencyconverter.data.ready

import io.reactivex.Completable
import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Rates
import javax.inject.Inject

class ReadyRatesImpl @Inject constructor() : ReadyRates {

    private var rates: Rates? = null

    override fun getReadyRates(): Single<Rates> {
        return if (rates == null || rates!!.rates.isNullOrEmpty()) {
            Single.error(NullPointerException("ReadyRates is empty"))
        } else {
            Single.just(rates)
        }
    }

    override fun setReadyRates(rates: Rates): Completable {
        this.rates = rates
        return Completable.complete()
    }

}