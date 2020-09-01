package ru.okcode.currencyconverter.model

import androidx.lifecycle.LiveData
import javax.inject.Inject

class RepositoryConfigImpl @Inject constructor(): RepositoryConfig {
    override val baseCurrencyCode: LiveData<String>
        get() = TODO("Not yet implemented")
}