package ru.okcode.currencyconverter.data.db.ready.decorator

import ru.okcode.currencyconverter.data.db.ready.ReadyRates
import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.Rates

class BaseChangeDecorator(source: ReadyRates, config: Config) : ReadyRatesDecorator(source) {

    override fun createReadyRates(rates: Rates): Rates {
        return super.createReadyRates(baseChange(rates))
    }

    private fun baseChange(rates: Rates): Rates {
        // TODO FIX IT
        return rates
    }
}