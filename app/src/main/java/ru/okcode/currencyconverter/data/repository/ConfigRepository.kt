package ru.okcode.currencyconverter.data.repository

import androidx.lifecycle.LiveData
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import ru.okcode.currencyconverter.data.model.Config

interface ConfigRepository {
    fun getConfig(): Single<Config>
}