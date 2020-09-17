package ru.okcode.currencyconverter.data.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiService {
    @GET("latest/EUR")
    fun getRatesAsync(): Deferred<RatesDto>
}