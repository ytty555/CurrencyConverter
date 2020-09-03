package ru.okcode.currencyconverter.model

import android.icu.util.CurrencyAmount

data class Config(
    val baseCurrencyCode: String,
    val baseCurrencyAmount: Double,
    val visibleCurrencies: Set<String>
)