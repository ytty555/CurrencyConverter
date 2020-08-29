package ru.okcode.currencyconverter.model

import android.icu.math.BigDecimal
import android.icu.util.Currency
import java.util.*

data class Rates(
    val baseCurrency: Currency,
    val rates: List<Rate>,
    val lastUpdate: Date,
    val nextUpdate: Date
)

data class Rate(
    val currency: Currency,
    val value: BigDecimal,
    val flagRes: Int?
)