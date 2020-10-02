package ru.okcode.currencyconverter.data.db.ready.decorator

import io.reactivex.Completable
import io.reactivex.Single
import ru.okcode.currencyconverter.data.db.ready.ReadyRates
import ru.okcode.currencyconverter.data.model.Rates

class BaseChangeDecorator(source: ReadyRates) : ReadyRatesDecorator(source) {

    override fun setReadyRates(rates: Rates): Completable {
        return super.setReadyRates(baseChange(rates))
    }

    private fun baseChange(rates: Rates): Rates {
        // TODO FIX IT
        return rates
    }
}