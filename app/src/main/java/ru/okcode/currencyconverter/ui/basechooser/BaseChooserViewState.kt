package ru.okcode.currencyconverter.ui.basechooser

import android.icu.util.Currency
import ru.okcode.currencyconverter.mvibase.MviViewState

data class BaseChooserViewState(
    val currency: Currency?,
    val flagRes: Int?,
    val displayValue: String,
    val error: Throwable?
) : MviViewState {
    companion object {
        fun idle(): BaseChooserViewState {
            return BaseChooserViewState(
                currency = null,
                flagRes = null,
                displayValue = "0",
                error = null
            )
        }
    }
}