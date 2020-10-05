package ru.okcode.currencyconverter.data.repository

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.Rates

interface ConfigRepository: Closeable {
    fun getConfig(): Flowable<Config>

    fun saveConfig(config: Config)
}