package ru.okcode.currencyconverter.model.repositories

import androidx.lifecycle.LiveData
import ru.okcode.currencyconverter.model.Config

interface ConfigRepository {
    val configDataSource: LiveData<Config>

    suspend fun changeBaseCurrency(baseCurrencyCode: String)
}