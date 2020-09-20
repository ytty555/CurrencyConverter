package ru.okcode.currencyconverter.ui.overview

import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.mvibase.MviViewState

data class OverviewViewState(
    val isLoading: Boolean,
    val rates: Rates,
    val error: Throwable?
) : MviViewState {
    companion object {
        fun idle() = OverviewViewState(
            isLoading = false,
            rates = Rates.idle(),
            error = null
        )
    }
}