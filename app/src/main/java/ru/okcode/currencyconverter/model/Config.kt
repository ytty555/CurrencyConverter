package ru.okcode.currencyconverter.model

import android.icu.util.CurrencyAmount

data class Config(
    val baseCurrencyCode: String,
    val baseCurrencyAmount: Double,
    val visibleCurrencies: Set<String>
) {
    companion object {
        fun getDefaultConfig(): Config {
            return Config(
                baseCurrencyCode = "EUR",
                baseCurrencyAmount = 1.0,
                visibleCurrencies = HashSet()
            )
        }
    }
}
