package ru.okcode.currencyconverter.data.repository

import androidx.lifecycle.LifecycleObserver
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.Rates

interface ConfigRepository: LifecycleObserver {
    fun getConfig(): Flowable<Config>

    fun saveConfig(config: Config)
}