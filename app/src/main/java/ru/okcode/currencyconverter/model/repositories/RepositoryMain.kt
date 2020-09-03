package ru.okcode.currencyconverter.model.repositories

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred
import ru.okcode.currencyconverter.model.Config
import ru.okcode.currencyconverter.model.Rates

interface RepositoryMain {
    val configDataSource: LiveData<Config>

    val cacheDataSource: LiveData<Rates>

    val readyRatesDataSource: LiveData<Rates>

    suspend fun refreshData(immediately: Boolean)

    fun getCachedRatesAsync(): Deferred<Rates>

    fun updateReadyRates(rates: Rates, config: Config)
}