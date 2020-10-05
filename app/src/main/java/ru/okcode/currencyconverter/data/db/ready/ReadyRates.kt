package ru.okcode.currencyconverter.data.db.ready

import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.Rates

interface ReadyRates {
    fun createReadyRates(rates: Rates): Rates
}