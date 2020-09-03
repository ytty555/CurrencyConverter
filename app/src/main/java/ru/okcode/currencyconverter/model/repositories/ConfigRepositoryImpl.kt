package ru.okcode.currencyconverter.model.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.okcode.currencyconverter.model.Config
import ru.okcode.currencyconverter.model.db.config.ConfigDao
import ru.okcode.currencyconverter.model.db.config.ConfigMapper
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(
    private val configDao: ConfigDao,
    private val configMapper: ConfigMapper
) : ConfigRepository {
    override val configDataSource: LiveData<Config>
        get() = Transformations.map(configDao.getConfig()) { configEntity ->
            configEntity?.let {
                configMapper.mapToModel(it)
            }
        }
}