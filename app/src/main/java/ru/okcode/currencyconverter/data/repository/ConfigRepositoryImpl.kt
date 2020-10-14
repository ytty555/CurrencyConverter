package ru.okcode.currencyconverter.data.repository

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import ru.okcode.currencyconverter.data.db.config.ConfigDao
import ru.okcode.currencyconverter.data.db.config.ConfigEntity
import ru.okcode.currencyconverter.data.db.config.ConfigMapper
import ru.okcode.currencyconverter.data.model.Config
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(
    private val configDao: ConfigDao,
    private val configMapper: ConfigMapper
) : ConfigRepository {

    private val disposables = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun checkIn() {
        checkForEmpty()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun checkOut() {
        disposables.clear()
    }

    private fun checkForEmpty() {
        val checkForEmptyConfigDisposable = configDao.getConfigSingle()
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onError = {
                    configDao.insertConfig(ConfigEntity.createDefaultConfig())
                }
            )
        disposables.add(checkForEmptyConfigDisposable)
    }

    override fun getConfigFlowable(): Flowable<Config> {
        return configDao.getConfig()
            .map { configEntity ->
                configMapper.mapToModel(configEntity)
            }
    }

    override fun getConfigSingle(): Single<Config> {
        return configDao.getConfigSingle()
            .subscribeOn(Schedulers.io())
            .map { configEntity ->
                configMapper.mapToModel(configEntity)
            }
    }

    override fun saveConfig(config: Config) {
        configDao.insertConfig(
            configMapper.mapToEntity(config)
        )
    }

}