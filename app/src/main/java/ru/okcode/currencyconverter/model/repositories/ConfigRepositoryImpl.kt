package ru.okcode.currencyconverter.model.repositories

import androidx.lifecycle.LiveData
import ru.okcode.currencyconverter.model.Config
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(): ConfigRepository {
    override val configDataSource: LiveData<Config>
        get() = TODO("Not yet implemented")
}