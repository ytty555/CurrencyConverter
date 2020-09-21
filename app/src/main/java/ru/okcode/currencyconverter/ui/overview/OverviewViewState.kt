package ru.okcode.currencyconverter.ui.overview

import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.mvibase.MviViewState
import ru.okcode.currencyconverter.ui.Destinations

data class OverviewViewState(
    val isLoading: Boolean,
    val switchingTo: Destinations?,
    val rates: Rates,
    val error: Throwable?
) : MviViewState {
    companion object {
        fun idle() = OverviewViewState(
            isLoading = false,
            switchingTo = null,
            rates = Rates.idle(),
            error = null
        )
    }
}

