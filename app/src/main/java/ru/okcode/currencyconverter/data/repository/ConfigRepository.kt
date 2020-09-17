package ru.okcode.currencyconverter.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred
import ru.okcode.currencyconverter.data.model.Config

interface ConfigRepository {
    val configDataSource: LiveData<Config>

    suspend fun changeBase(baseCurrencyCode: String, amount: Float)

    fun getConfigAsync(): Deferred<Config?>
}