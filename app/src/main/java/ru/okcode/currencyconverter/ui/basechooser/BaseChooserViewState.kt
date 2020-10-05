package ru.okcode.currencyconverter.ui.basechooser

import android.icu.util.Currency
import ru.okcode.currencyconverter.mvibase.MviViewState
import ru.okcode.currencyconverter.ui.Destinations

data class BaseChooserViewState(
    val currencyInfo: Currency,
    val displayValue: String,
    val currencyAmount: Float?,
    val message: String?,
    val switchingTo: Destinations?,
    val error: Throwable?
) : MviViewState