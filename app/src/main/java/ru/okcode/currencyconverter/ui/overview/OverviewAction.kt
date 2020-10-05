package ru.okcode.currencyconverter.ui.overview

import ru.okcode.currencyconverter.mvibase.MviAction

sealed class OverviewAction : MviAction {
    data class UpdateRawRatesAction(val nothingToUpdateMessageShow: Boolean): OverviewAction()
    object EditCurrencyListAction : OverviewAction()
    data class ChangeBaseCurrencyAction(val currencyCode: String, val currentCurrencyAmount: Float) :
        OverviewAction()
}