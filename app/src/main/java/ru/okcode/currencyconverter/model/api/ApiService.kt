package ru.okcode.currencyconverter.model.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory

interface ApiService {

    @GET("latest")
    fun getAllLatest(): Call<RatesData>

    companion object {
        private const val API_PATH = "https://api.exchangeratesapi.io/"

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(API_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }

    }
}