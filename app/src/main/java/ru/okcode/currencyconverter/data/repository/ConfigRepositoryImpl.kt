package ru.okcode.currencyconverter.data.repository

import io.reactivex.Observable
import ru.okcode.currencyconverter.data.db.config.ConfigDao
import ru.okcode.currencyconverter.data.db.config.ConfigMapper
import ru.okcode.currencyconverter.data.model.Config
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(
    private val configDao: ConfigDao,
    private val configMapper: ConfigMapper
) : ConfigRepository {

    override fun getConfigObservable(): Observable<Config> {
        return configDao.getConfig()
            .toObservable()
            .map {
                configMapper.mapToModel(it)
            }
    }

    override fun saveConfig(config: Config) {
        configDao.insertConfig(
            configMapper.mapToEntity(config)
        )
    }

}