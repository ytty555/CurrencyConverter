package ru.okcode.currencyconverter.model.repositories

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred
import ru.okcode.currencyconverter.model.Config
import ru.okcode.currencyconverter.model.Rates

interface ConfigRepository {
    val configDataSource: LiveData<Config>

    suspend fun changeBase(baseCurrencyCode: String, amount: Float)

    fun getConfigAsync(): Deferred<Config?>
}