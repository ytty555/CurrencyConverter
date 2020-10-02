package ru.okcode.currencyconverter.data.repository

import io.reactivex.Observable
import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Rates

interface RawRatesRepository {
    fun getRatesSingle(): Single<Rates>

    fun getRatesObservable(): Observable<Rates>
}