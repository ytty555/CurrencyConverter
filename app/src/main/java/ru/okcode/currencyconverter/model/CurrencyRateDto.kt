package ru.okcode.currencyconverter.model

import java.util.*

data class CurrencyRateDto(
    val currency: CurrencyEnum,
    var baseCurrency: CurrencyEnum,
    val rateToEUR: Double,
    var rateToBaseCurrency: Double,
    val rateDate: Date
) {
    fun changeBaseCurrency(newBase: CurrencyRateDto) {
        if (baseCurrency == newBase.currency) return
        baseCurrency = newBase.currency
        rateToBaseCurrency = rateToEUR / newBase.rateToEUR
    }
}