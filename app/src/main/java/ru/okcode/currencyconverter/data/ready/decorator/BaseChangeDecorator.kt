package ru.okcode.currencyconverter.data.ready.decorator

import io.reactivex.Completable
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.data.ready.ReadyRates

class BaseChangeDecorator(source: ReadyRates) : ReadyRatesDecorator(source) {

    override fun setReadyRates(rates: Rates): Completable {
        return super.setReadyRates(baseChange(rates))
    }

    private fun baseChange(rates: Rates): Rates {
        // TODO FIX IT
        return rates
    }
}