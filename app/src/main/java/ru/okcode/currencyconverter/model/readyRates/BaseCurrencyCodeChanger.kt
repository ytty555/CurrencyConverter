package ru.okcode.currencyconverter.model.readyRates

import ru.okcode.currencyconverter.model.Rates

class BaseCurrencyCodeChanger(
    source: ReadyRates,
    private val baseCurrencyCode: String?
) : RatesDecorator(source) {
    override suspend fun writeRates(rates: Rates) {
        super.writeRates(changeBaseCurrencyCode(rates))
    }

    private fun changeBaseCurrencyCode(rates: Rates): Rates {
        if (baseCurrencyCode == rates.baseCurrency.currencyCode || baseCurrencyCode.isNullOrEmpty()) {
            // Do nothing. Return input rates
            return rates
        }

        // TODO FIX IT
        return  rates
    }
}