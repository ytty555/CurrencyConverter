package ru.okcode.currencyconverter.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Rates

interface CacheRepository {
    fun getRates(): Observable<Rates>

    fun saveCache(rates: Rates): Completable
}
