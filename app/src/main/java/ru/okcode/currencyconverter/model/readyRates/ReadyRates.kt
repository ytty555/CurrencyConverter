package ru.okcode.currencyconverter.model.readyRates

import ru.okcode.currencyconverter.model.Rates

interface ReadyRates {
    suspend fun writeRates(rates: Rates)
}