package ru.okcode.currencyconverter.ui

sealed class Destinations {
    object EditCurrencyListDestination : Destinations()
    data class ChangeBaseCurrencyDestination(
        val currencyCode: String,
        val currentCurrencyAmount: Float
    ) : Destinations()
    object OverviewRatesDestination: Destinations()
}