package ru.okcode.currencyconverter.model.repositories

import androidx.lifecycle.LiveData
import ru.okcode.currencyconverter.model.Config
import ru.okcode.currencyconverter.model.Rates

interface ReadyRepository {
    val readyRatesDataSource: LiveData<Rates>

    fun updateReadyRates(rates: Rates, config: Config)
}