package ru.okcode.currencyconverter.data.db.ready

import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.Rates
import javax.inject.Inject

class ReadyRatesImpl @Inject constructor() : ReadyRates {

    override fun createReadyRates(rates: Rates): Rates {
        return rates
    }

}