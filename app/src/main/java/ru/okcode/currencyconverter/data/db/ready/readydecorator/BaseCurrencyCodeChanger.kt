package ru.okcode.currencyconverter.data.db.ready.readydecorator

import android.icu.math.BigDecimal
import android.icu.util.Currency
import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.Rate
import ru.okcode.currencyconverter.data.model.Rates


class BaseCurrencyCodeChanger(
    source: ReadyRates,
    private val config: Config
) : RatesDecorator(source) {
    override suspend fun writeRates(rates: Rates) {
        super.writeRates(changeBaseCurrency(rates))
    }

    private fun changeBaseCurrency(rates: Rates): Rates {

        val newBaseCurrencyCode = config.baseCurrencyCode

        val newBaseCurrency = Currency.getInstance(newBaseCurrencyCode)

        val newBaseCurrencyRateToEuro: BigDecimal =
            rates.rates
                .filter { rate -> rate.currency.currencyCode == newBaseCurrencyCode }[0]
                .rateToEur

        val newBaseCurrencyAmount = config.baseCurrencyAmount

        val newRatesList: List<Rate> = rates.rates.map { oldRate ->
            val newRateToBase: BigDecimal = calcNewRateToBase(oldRate, newBaseCurrencyRateToEuro)
            Rate(
                currency = oldRate.currency,
                rateToBase = newRateToBase,
                rateToEur = oldRate.rateToEur,
                sum = newRateToBase.multiply(BigDecimal.valueOf(newBaseCurrencyAmount.toDouble())),
                priorityPosition = oldRate.priorityPosition,
                flagRes = oldRate.flagRes
            )
        }

        return Rates(
            baseCurrency = newBaseCurrency,
            baseCurrencyAmount = newBaseCurrencyAmount,
            baseCurrencyRateToEuro = newBaseCurrencyRateToEuro,
            rates = newRatesList,
            timeLastUpdateUnix = rates.timeLastUpdateUnix,
            timeNextUpdateUnix = rates.timeNextUpdateUnix
        )
    }

    private fun calcNewRateToBase(
        rate: Rate,
        baseCurrencyRateToEuro: BigDecimal
    ): BigDecimal {
        return rate.rateToEur.divide(baseCurrencyRateToEuro)
    }
}