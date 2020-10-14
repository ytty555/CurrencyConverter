package ru.okcode.currencyconverter.data.model.ready.decorator

import ru.okcode.currencyconverter.data.model.ready.ReadyRates
import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.Rates

class CurrencyVisibilityChangeDecorator(source: ReadyRates, config: Config) : ReadyRatesDecorator(source) {

    override fun createReadyRates(rates: Rates): Rates {
        return super.createReadyRates(currencyListChange(rates))
    }

    private fun currencyListChange(rates: Rates): Rates {
        // TODO FIX IT
        return rates
    }
}