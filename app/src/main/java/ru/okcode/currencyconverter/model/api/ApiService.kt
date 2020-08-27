package ru.okcode.currencyconverter.model.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("latest/USD")
    fun getRatesAsync(): Deferred<RatesDto>
}