package ru.okcode.currencyconverter.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import ru.okcode.currencyconverter.data.model.Rates

interface CacheRepository {

    fun getRatesObservable(): Flowable<Rates>

    fun getRatesSingle(): Single<Rates>

    fun saveCache(rates: Rates): Completable
}
