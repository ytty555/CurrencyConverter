package ru.okcode.currencyconverter.data.model

data class Config(
    val baseCurrencyCode: String,
    val baseCurrencyAmount: Float,
    val configuredCurrencies: List<ConfiguredCurrency>
)
