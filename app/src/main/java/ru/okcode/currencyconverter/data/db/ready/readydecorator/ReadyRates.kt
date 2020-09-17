package ru.okcode.currencyconverter.data.db.ready.readydecorator

import ru.okcode.currencyconverter.data.model.Rates

interface ReadyRates {
    suspend fun writeRates(rates: Rates)
}