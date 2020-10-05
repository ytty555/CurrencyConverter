package ru.okcode.currencyconverter.data.db.ready.decorator

import io.reactivex.Completable
import io.reactivex.Single
import ru.okcode.currencyconverter.data.db.ready.ReadyRates
import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.Rates

class CurrencyPriorityChangeDecorator(source: ReadyRates, config: Config) : ReadyRatesDecorator(source) {

    override fun createReadyRates(rates: Rates): Rates {
        return super.createReadyRates(currencyPriorityChange(rates))
    }

    private fun currencyPriorityChange(rates: Rates): Rates {
        // TODO FIX IT
        return rates
    }
}