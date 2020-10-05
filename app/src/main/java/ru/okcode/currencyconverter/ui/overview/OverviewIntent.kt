package ru.okcode.currencyconverter.ui.overview

import ru.okcode.currencyconverter.mvibase.MviIntent

sealed class OverviewIntent : MviIntent {
    data class UpdateRawRatesIntent(val nothingToUpdateMessageShow: Boolean): OverviewIntent()
    object EditCurrencyListIntent : OverviewIntent()
    data class ChangeBaseCurrencyIntent(
        val currencyCode: String,
        val currentAmount: Float
    ) : OverviewIntent()
}