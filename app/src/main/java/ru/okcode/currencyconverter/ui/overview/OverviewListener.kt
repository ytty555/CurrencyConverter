package ru.okcode.currencyconverter.ui.overview

interface OverviewListener {
    fun onClickRateItem(currencyCode: String, currencyAmount: Float)

    fun onClickEditRatesList()

    fun onUpdateRates()
}