package ru.okcode.currencyconverter.data.repository

import io.reactivex.Observable
import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Rates

interface NetworkRepository {
    fun getRatesObservable(): Observable<Rates>

    fun getRatesSingle(): Single<Rates>
}