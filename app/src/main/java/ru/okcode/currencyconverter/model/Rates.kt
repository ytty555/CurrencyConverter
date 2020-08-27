package ru.okcode.currencyconverter.model

import android.icu.math.BigDecimal
import android.icu.util.Currency
import java.util.*

data class Rates(
    val actualDate: Date,
    val baseCurrency: Currency,
    val rates: List<Rate>
)

data class Rate(
    val currency: Currency,
    val value: BigDecimal,
    val flagRes: Int?
)