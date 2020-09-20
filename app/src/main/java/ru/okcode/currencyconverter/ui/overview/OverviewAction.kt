package ru.okcode.currencyconverter.ui.overview

import ru.okcode.currencyconverter.mvibase.MviAction

sealed class OverviewAction : MviAction {
    object LoadAllRatesAction : OverviewAction()
    object EditCurrencyListAction : OverviewAction()
    data class ChangeBaseCurrencyAction(val currencyCode: String, val currentAmount: Float) :
        OverviewAction()
}