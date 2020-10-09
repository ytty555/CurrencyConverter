package ru.okcode.currencyconverter.ui.overview

import ru.okcode.currencyconverter.mvibase.MviIntent

sealed class OverviewIntent : MviIntent {
    object ListenCacheAndConfigHaveChangedIntent: OverviewIntent()
    data class InstantiateStateIntent(val state: OverviewViewState): OverviewIntent()
    data class UpdateRatesIntent(val nothingToUpdateMessageShow: Boolean): OverviewIntent()
    object EditCurrencyListIntent : OverviewIntent()
    data class ChangeBaseCurrencyIntent(
        val currencyCode: String,
        val currentAmount: Float
    ) : OverviewIntent()
}