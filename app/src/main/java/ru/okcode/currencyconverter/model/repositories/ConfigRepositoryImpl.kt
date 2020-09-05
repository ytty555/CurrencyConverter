package ru.okcode.currencyconverter.model.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.okcode.currencyconverter.model.Config
import ru.okcode.currencyconverter.model.db.config.ConfigDao
import ru.okcode.currencyconverter.model.db.config.ConfigEntity
import ru.okcode.currencyconverter.model.db.config.ConfigMapper
import javax.inject.Inject

private const val TAG = "ConfigRepositoryImpl"

class ConfigRepositoryImpl @Inject constructor(
    private val configDao: ConfigDao,
    private val configMapper: ConfigMapper
) : ConfigRepository {

    override val configDataSource: LiveData<Config>
        get() = Transformations.map(configDao.getConfigDataSource()) { configEntity ->
            if (configEntity == null) {
                val defaultConfig: ConfigEntity = ConfigEntity.createDefaultConfig()

                GlobalScope.launch {
                    configDao.insertConfig(defaultConfig)
                }

                configMapper.mapToModel(defaultConfig)
            } else {
                configMapper.mapToModel(configEntity)
            }
        }

    override suspend fun changeBaseCurrency(baseCurrencyCode: String) {
        configDao.updateBaseCurrency(baseCurrencyCode)
    }

    override fun getConfigAsync(): Deferred<Config?> {
        return GlobalScope.async {
            configMapper.mapToModel(configDao.getConfig())
        }
    }

}