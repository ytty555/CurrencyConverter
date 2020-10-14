package ru.okcode.currencyconverter.ui.overview

import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.mvibase.MviViewState

data class OverviewViewState(
    val isLoading: Boolean,
    val readyData: Rates,
    val error: Throwable?,
    val changeBaseCurrency: Pair<String, Float>?,
    val editCurrencyList: Boolean
): MviViewState {
    companion object {
        fun idle(): OverviewViewState {
            return OverviewViewState(
                isLoading = false,
                readyData = Rates.idle(),
                error = null,
                changeBaseCurrency = null,
                editCurrencyList = false
            )
        }
    }
}


