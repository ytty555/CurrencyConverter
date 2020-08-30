package ru.okcode.currencyconverter.model.readyRates

import android.icu.math.BigDecimal

data class Rates(
    val baseCurrencyCode: String,
    val baseCurrencyAmount: BigDecimal =
        BigDecimal.valueOf(1.0),
    val rates: List<Rate>,
    val timeLastUpdateUnix: Long,
    val timeNextUpdateUnix: Long
)

data class Rate(
    val currencyCode: String,
    val rate: BigDecimal,
    val sum: BigDecimal,
    val priorityPosition: Int = 0,
    val flagRes: Int?
)