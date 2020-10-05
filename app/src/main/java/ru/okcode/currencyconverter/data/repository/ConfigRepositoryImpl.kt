package ru.okcode.currencyconverter.data.repository

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import ru.okcode.currencyconverter.data.db.config.ConfigDao
import ru.okcode.currencyconverter.data.db.config.ConfigEntity
import ru.okcode.currencyconverter.data.db.config.ConfigMapper
import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.Rates
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(
    private val configDao: ConfigDao,
    private val configMapper: ConfigMapper
) : ConfigRepository {

    private val disposables = CompositeDisposable()
    private val configProcessor = BehaviorProcessor.create<Config>()

    init {
        checkForEmpty()

        configDao.getConfig()
            .map {
                configMapper.mapToModel(it)
            }
            .subscribe(configProcessor)
    }

    private fun checkForEmpty() {
       val disposable = configDao.checkForEmptyConfig()
           .subscribeOn(Schedulers.io())
           .subscribeBy(
               onError = {
                   configDao.insertConfig(ConfigEntity.createDefaultConfig())
               }
           )
        disposables.add(disposable)
    }

    override fun getConfig(): Flowable<Config> {
        return configProcessor
            .doOnNext{
                Log.e(">>> 1c >>>", "$it")
            }

    }

    override fun saveConfig(config: Config) {
        configDao.insertConfig(
            configMapper.mapToEntity(config)
        )
    }

    override fun onClose() {
        disposables.clear()
    }

}