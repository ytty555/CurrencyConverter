package ru.okcode.currencyconverter.data.model.ready

import ru.okcode.currencyconverter.data.model.Rate
import ru.okcode.currencyconverter.data.model.Rates
import javax.inject.Inject

class ReadyRatesImpl @Inject constructor() : ReadyRates {

    override fun createReadyRates(rates: Rates): Rates {
        val visibleRatesList: List<Rate> = rates.rates.filter { it.isVisible }
        return rates.copy(
            rates = visibleRatesList
        )
    }

}