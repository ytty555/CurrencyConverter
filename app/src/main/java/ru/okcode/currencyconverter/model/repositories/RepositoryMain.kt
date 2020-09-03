package ru.okcode.currencyconverter.model.repositories

import androidx.lifecycle.LiveData
import ru.okcode.currencyconverter.model.Rates

interface RepositoryMain {
    val rawRates: LiveData<Rates>

    val baseCurrencyCode: LiveData<String>

    suspend fun refreshData(immediately: Boolean)

    suspend fun updateBaseCurrencyCode(code: String)

    suspend fun updateBaseCurrencyAmount(amount: Double)
}