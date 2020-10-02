package ru.okcode.currencyconverter.data.repository

import io.reactivex.Observable
import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.Rates

interface ConfigRepository {
    fun getConfigSingle(): Single<Config>

    fun getConfigObservable(): Observable<Rates>
}