package ru.okcode.currencyconverter.currencyrates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class CurrencyRatesViewModelFactory: ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyRatesViewModel::class.java)) {
            return CurrencyRatesViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}