package ru.okcode.currencyconverter.data.ready

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Rates
import javax.inject.Inject

class ReadyRatesImpl @Inject constructor() : ReadyRates {

    private var rates: Rates? = null

    init {
        Log.e("qq", "ReadyRatesImpl initialization!!!")
    }

    override fun getReadyRates(): Single<Rates> {
        Log.e("qq", "ReadyRatesImpl rates $rates")
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