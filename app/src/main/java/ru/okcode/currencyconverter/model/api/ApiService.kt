package ru.okcode.currencyconverter.model.api

import ru.okcode.currencyconverter.model.RatesData
import ru.okcode.currencyconverter.model.Result

interface ApiService {

    suspend fun getRates(): Result<RatesData>

}