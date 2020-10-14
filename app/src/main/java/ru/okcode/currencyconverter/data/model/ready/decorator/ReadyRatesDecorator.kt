package ru.okcode.currencyconverter.data.model.ready.decorator

import ru.okcode.currencyconverter.data.model.ready.ReadyRates
import ru.okcode.currencyconverter.data.model.Rates

open class ReadyRatesDecorator(
    private val wrapped: ReadyRates
) : ReadyRates {

    override fun createReadyRates(rates: Rates): Rates {
        return wrapped.createReadyRates(rates)
    }
}