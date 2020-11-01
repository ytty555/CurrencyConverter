package ru.okcode.currencyconverter.data.model.ready.decorator

import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.ConfiguredCurrency
import ru.okcode.currencyconverter.data.model.Rate
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.data.model.ready.ReadyRates

class CurrencyPriorityAndVisibilityChangeDecorator(
    source: ReadyRates,
    private val config: Config
) : ReadyRatesDecorator(source) {

    override fun createReadyRates(rates: Rates): Rates {
        return super.createReadyRates(currencyPriorityChange(rates))
    }

    private fun currencyPriorityChange(previousRates: Rates): Rates {
        val configuredCurrencies: List<ConfiguredCurrency> = config.configuredCurrencies

        val newRatesList: List<Rate> = previousRates.rates.map { rate ->
            val currencyCode = rate.currencyCode
            val configuredCurrency = configuredCurrencies.find {
                it.currencyCode == currencyCode
            }
            if (configuredCurrency == null) {
                return@map rate
            } else {
                return@map rate.copy(
                    priorityPosition = configuredCurrency.positionInList,
                    isVisible = configuredCurrency.isVisible
                )
            }
        }
            .sortedBy { it.priorityPosition }

        return previousRates.copy(
            rates = newRatesList
        )
    }
}