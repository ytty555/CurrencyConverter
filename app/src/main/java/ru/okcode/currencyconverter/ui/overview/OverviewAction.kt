package ru.okcode.currencyconverter.ui.overview

import ru.okcode.currencyconverter.mvibase.MviAction

sealed class OverviewAction : MviAction {
    object ListenCacheAndConfigHaveChangedAction : OverviewAction()
    data class InstantiateStateAction(val state: OverviewViewState) : OverviewAction()
    object UpdateRatesAction : OverviewAction()
    object EditCurrencyListAction : OverviewAction()
    data class ChangeBaseCurrencyAction(
        val currencyCode: String,
        val currentCurrencyAmount: Float
    ) : OverviewAction()
}