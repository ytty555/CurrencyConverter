package ru.okcode.currencyconverter.data.repository

import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Config

interface ConfigRepository {
    fun getConfig(): Single<Config>
}