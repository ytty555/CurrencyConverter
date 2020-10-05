package ru.okcode.currencyconverter.data.network

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {
    @GET("latest/EUR")
    fun getRatesObservable(): Observable<RatesDto>

    @GET("latest/EUR")
    fun getRatesSingle(): Single<RatesDto>
}