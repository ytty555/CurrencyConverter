package ru.okcode.currencyconverter.model.readyRates

import ru.okcode.currencyconverter.model.Rates

abstract class RatesDecorator(private val wrapped: ReadyRates) : ReadyRates {
    override fun writeRates(rates: Rates) {
        wrapped.writeRates(rates)
    }
}