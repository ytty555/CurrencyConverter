package ru.okcode.currencyconverter.data.ready

import io.reactivex.Completable
import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Rates

interface ReadyRates {

    fun getReadyRates(): Single<Rates>

    fun setReadyRates(rates: Rates): Completable
}