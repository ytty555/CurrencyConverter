package ru.okcode.currencyconverter.model

import android.icu.math.BigDecimal
import android.icu.util.Currency

data class Rates(
    val baseCurrency: Currency,
    val baseCurrencyAmount: Double = 1.0,
    val baseCurrencyRateToEuro: BigDecimal,
    val rates: List<Rate>,
    val timeLastUpdateUnix: Long,
    val timeNextUpdateUnix: Long
)

data class Rate(
    val currency: Currency,
    val rateToBase: BigDecimal,
    val rateToEur: BigDecimal,
    val sum: BigDecimal,
    val priorityPosition: Int = 0,
    val flagRes: Int?
)