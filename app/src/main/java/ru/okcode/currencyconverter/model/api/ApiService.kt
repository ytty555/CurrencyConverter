package ru.okcode.currencyconverter.model.api

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }

    }
}