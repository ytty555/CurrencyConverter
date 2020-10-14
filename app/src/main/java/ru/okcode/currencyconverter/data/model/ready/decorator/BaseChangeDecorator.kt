package ru.okcode.currencyconverter.data.model.ready.decorator

import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.Rate
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.data.model.ready.ReadyRates

class BaseChangeDecorator(source: ReadyRates, private val config: Config) :
    ReadyRatesDecorator(source) {

    override fun createReadyRates(rates: Rates): Rates {
        return super.createReadyRates(baseChange(rates))
    }

    // Apply base currency config changes
    private fun baseChange(previousRates: Rates): Rates {

        // If base currency config data has not changed then return initial rates
        if (previousRates.baseCurrencyCode == config.baseCurrencyCode &&
            previousRates.baseCurrencyAmount == config.baseCurrencyAmount
        ) {
            return previousRates
        }

        // If base currency config data has changed then return new rates
        val baseCurrencyRateToEuro: Float =
            getRateToEuro(previousRates, config.baseCurrencyCode)

        val ratesList: List<Rate> =
            getConfiguredRatesList(
                previousRatesList = previousRates.rates,
                baseCurrencyAmount = config.baseCurrencyAmount,
                baseCurrencyRateToEuro = baseCurrencyRateToEuro
            )

        return previousRates.copy(
            baseCurrencyCode = config.baseCurrencyCode,
            baseCurrencyAmount = config.baseCurrencyAmount,
            baseCurrencyRateToEuro = baseCurrencyRateToEuro,
            rates = ratesList
        )
    }

    private fun getRateToEuro(
        previousRates: Rates,
        newBaseCurrencyCode: String
    ): Float {
        // If baseCurrencyCode hasn't changed then return previous baseCurrencyRateToEuro
        if (previousRates.baseCurrencyCode == newBaseCurrencyCode) {
            return previousRates.baseCurrencyRateToEuro
        }

        // Else return rateToEuro from appropriate currency in ratesList of rates
        val appropriateRateList =
            previousRates.rates.filter { rate ->
                rate.currencyCode == newBaseCurrencyCode
            }

        if (appropriateRateList.isNotEmpty()) {
            return appropriateRateList[0].rateToEur
        } else {
            throw Throwable("newBaseCurrencyCode hasn't found in the rates list")
        }

    }

    private fun getConfiguredRatesList(
        previousRatesList: List<Rate>,
        baseCurrencyAmount: Float,
        baseCurrencyRateToEuro: Float
    ): List<Rate> {
        return previousRatesList.map { rate ->
            val newRateToBase = rate.rateToEur / baseCurrencyRateToEuro
            val newSum = newRateToBase * baseCurrencyAmount

            rate.copy(
                rateToBase = newRateToBase,
                sum = newSum
            )
        }
    }
}