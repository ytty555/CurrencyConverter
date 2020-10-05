package ru.okcode.currencyconverter.data.db.ready.decorator

import io.reactivex.Completable
import io.reactivex.Single
import ru.okcode.currencyconverter.data.db.ready.ReadyRates
import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.Rates

open class ReadyRatesDecorator(
    private val wrapped: ReadyRates
) : ReadyRates {

    override fun createReadyRates(rates: Rates): Rates {
        return wrapped.createReadyRates(rates)
    }
}