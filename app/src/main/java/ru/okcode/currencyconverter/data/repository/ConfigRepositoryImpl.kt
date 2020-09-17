package ru.okcode.currencyconverter.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.db.config.ConfigDao
import ru.okcode.currencyconverter.data.db.config.ConfigEntity
import ru.okcode.currencyconverter.data.db.config.ConfigMapper
import javax.inject.Inject

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

    override suspend fun changeBase(baseCurrencyCode: String, amount: Float) {
        configDao.updateBaseCurrency(baseCurrencyCode, amount)
    }

    override fun getConfigAsync(): Deferred<Config?> {
        return GlobalScope.async {
            configMapper.mapToModel(configDao.getConfig())
        }
    }

}