package ru.okcode.currencyconverter.data.model.ready

import ru.okcode.currencyconverter.data.model.Rates

interface ReadyRates {
    fun createReadyRates(rates: Rates): Rates
}