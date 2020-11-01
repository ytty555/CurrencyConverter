package ru.okcode.currencyconverter.data.repository

import androidx.lifecycle.LifecycleObserver
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Config

interface ConfigRepository : LifecycleObserver {
    fun getConfigFlowable(): Flowable<Config>

    fun getConfigSingle(): Single<Config>

    fun saveConfig(config: Config): Completable
}