package ru.okcode.currencyconverter.model.repositories

import androidx.lifecycle.LiveData
import ru.okcode.currencyconverter.model.repositories.RepositoryConfig
import javax.inject.Inject

class RepositoryConfigImpl @Inject constructor(): RepositoryConfig {
    override val baseCurrencyCode: LiveData<String>
        get() = TODO("Not yet implemented")
}