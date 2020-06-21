package ru.okcode.currencyconverter.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import java.util.*


data class CommonRates(
    val commonRatesDataSet: CommonRatesDataSet,
    val commonRatesList: List<CommonRateItem>
)

data class CommonRatesDataSet(
    val actualDate: Date,
    var baseCurrencyCode: String,
    var baseCurrencyRateToEuro: Double,
    var baseCurrencyAmount: Float
) {
    val baseCurrencySymbol: String
        get() = Currency.getInstance(baseCurrencyCode).symbol
}

data class CommonRateItem(
    val currencyCode: String,
    @DrawableRes val flagRes: Int,
    @StringRes val fullNameRes: Int,
    val rateToEuro: Double,
    var rateToBase: Double
) {
    val currencySymbol: String
        get() = Currency.getInstance(currencyCode).symbol
}