package ru.okcode.currencyconverter.data.repository

import androidx.lifecycle.LiveData
import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.Rates

interface ReadyRepository {
    val readyRatesDataSource: LiveData<Rates>

    suspend fun updateReadyRates(rates: Rates, config: Config)
}