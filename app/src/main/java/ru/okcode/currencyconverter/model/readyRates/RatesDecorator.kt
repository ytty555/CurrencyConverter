package ru.okcode.currencyconverter.model.readyRates

abstract class RatesDecorator(private val wrapped: ReadyRates) : ReadyRates {
    override fun writeRates(rates: Rates) {
        wrapped.writeRates(rates)
    }
}