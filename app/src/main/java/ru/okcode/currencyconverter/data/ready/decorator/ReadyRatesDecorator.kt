package ru.okcode.currencyconverter.data.ready.decorator

import io.reactivex.Completable
import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.data.ready.ReadyRates

open class ReadyRatesDecorator(
    private val wrapped: ReadyRates
) : ReadyRates {

    override fun getReadyRates(): Single<Rates> {
        return wrapped.getReadyRates()
    }

    override fun setReadyRates(rates: Rates): Completable {
       return wrapped.setReadyRates(rates)
    }
}