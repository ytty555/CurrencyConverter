package ru.okcode.currencyconverter.model.api

import com.squareup.moshi.Json

data class RatesDto(
    @Json(name = "result") val result: String,
    @Json(name = "time_last_update_utc") val timeLastUpdateUtc: String,
    @Json(name = "time_next_update_utc") val timeNextUpdateUtc: String,
    @Json(name = "base_code") val baseCode: String,
    @Json(name = "conversion_rates") val conversionRates: Map<String, Double>
)
