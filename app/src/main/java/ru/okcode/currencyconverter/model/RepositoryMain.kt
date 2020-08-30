package ru.okcode.currencyconverter.model

import androidx.lifecycle.LiveData
import ru.okcode.currencyconverter.model.readyRates.Rates

interface RepositoryMain {
    val readyRates: LiveData<Rates>

    suspend fun refreshData(immidiately: Boolean)
}