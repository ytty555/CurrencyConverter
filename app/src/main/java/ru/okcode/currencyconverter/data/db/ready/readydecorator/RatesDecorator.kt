package ru.okcode.currencyconverter.data.db.ready.readydecorator

import ru.okcode.currencyconverter.data.model.Rates

abstract class RatesDecorator(private val wrapped: ReadyRates) : ReadyRates {
    override suspend fun writeRates(rates: Rates) {
        wrapped.writeRates(rates)
    }
}