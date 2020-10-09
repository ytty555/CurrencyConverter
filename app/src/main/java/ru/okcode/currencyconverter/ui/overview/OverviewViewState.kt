package ru.okcode.currencyconverter.ui.overview

import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.mvibase.MviViewState

sealed class OverviewViewState : MviViewState {
    object Loading : OverviewViewState()
    data class ReadyData(val rates: Rates) : OverviewViewState()
    data class Failure(val error: Throwable) : OverviewViewState()
    data class ChangeBaseCurrency(val currencyCode: String, val currencyAmount: Float) :
        OverviewViewState()
    object EditCurrencyList: OverviewViewState()
}

