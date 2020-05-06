package ru.okcode.currencyconverter.currencyrates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.okcode.currencyconverter.model.DefaultRatesRepository
import ru.okcode.currencyconverter.model.RatesRepository
import ru.okcode.currencyconverter.model.api.ApiService
import ru.okcode.currencyconverter.model.db.RatesDatabase
import java.lang.IllegalArgumentException

class CurrencyRatesViewModelFactory(
    private val ratesRepository: RatesRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyRatesViewModel::class.java)) {
            return CurrencyRatesViewModel(ratesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}