package ru.okcode.currencyconverter.data.model

import android.icu.math.BigDecimal
import android.icu.util.Currency

data class Rates(
    val baseCurrency: Currency,
    val baseCurrencyAmount: Float = 1f,
    val baseCurrencyRateToEuro: BigDecimal,
    val rates: List<Rate>,
    val timeLastUpdateUnix: Long,
    val timeNextUpdateUnix: Long
) {
    companion object {
        fun idle(): Rates {
            return Rates(
                baseCurrency = Currency.getInstance("EUR"),
                baseCurrencyAmount = 1f,
                baseCurrencyRateToEuro = BigDecimal.valueOf(1.0),
                timeLastUpdateUnix = 0,
                timeNextUpdateUnix = 0,
                rates = emptyList()
            )
        }
    }
}

data class Rate(
    val currency: Currency,
    val rateToBase: BigDecimal,
    val rateToEur: BigDecimal,
    val sum: BigDecimal,
    val priorityPosition: Int = 0,
    val flagRes: Int?,
    val isVisible: Boolean = true
)