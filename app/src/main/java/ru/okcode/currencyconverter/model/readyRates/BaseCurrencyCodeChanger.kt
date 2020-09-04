package ru.okcode.currencyconverter.model.readyRates

import android.util.Log
import ru.okcode.currencyconverter.model.Rates

private const val TAG = "BaseCurrencyCodeChanger"

class BaseCurrencyCodeChanger(
    source: ReadyRates,
    private val baseCurrencyCode: String?
) : RatesDecorator(source) {
    override suspend fun writeRates(rates: Rates) {
        super.writeRates(changeBaseCurrencyCode(rates))
    }

    private fun changeBaseCurrencyCode(rates: Rates): Rates {
        Log.e(TAG, "old = ${rates.baseCurrency.currencyCode} => new = $baseCurrencyCode")
        if (baseCurrencyCode == rates.baseCurrency.currencyCode || baseCurrencyCode.isNullOrEmpty()) {
            // Do nothing. Return input rates
            Log.e(TAG, "changeBaseCurrencyCode Do NOTHING!")

            return rates
        }

            Log.e(TAG, "changeBaseCurrencyCode Do some thing!")
        // TODO FIX IT
        return  rates
    }
}