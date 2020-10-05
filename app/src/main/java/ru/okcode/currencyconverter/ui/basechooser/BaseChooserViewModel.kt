package ru.okcode.currencyconverter.ui.basechooser

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class BaseChooserViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val currencyCode: String? =
        savedStateHandle.get(ARG_CURRENCY_CODE)

    private val currencyAmount: Float? =
        savedStateHandle.get(ARG_CURRENCY_AMOUNT)

    companion object {
        const val ARG_CURRENCY_CODE = "arg_currency_code"
        const val ARG_CURRENCY_AMOUNT = "arg_currency_amount"
    }

}