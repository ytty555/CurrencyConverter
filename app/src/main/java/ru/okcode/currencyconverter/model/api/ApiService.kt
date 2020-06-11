package ru.okcode.currencyconverter.model.api

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("latest")
    fun getAllLatest(): Observable<RatesData>

    companion object {
        private const val API_PATH = "https://api.exchangeratesapi.io/"

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(API_PATH)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }

    }
}