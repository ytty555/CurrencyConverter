package ru.okcode.currencyconverter.data.repository

import androidx.lifecycle.LifecycleObserver
import io.reactivex.Completable
import io.reactivex.Flowable
import ru.okcode.currencyconverter.data.model.Config

interface ConfigRepository : LifecycleObserver {
    fun getConfig(): Flowable<Config>

    fun saveConfig(config: Config): Completable
}