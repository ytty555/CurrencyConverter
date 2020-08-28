package ru.okcode.currencyconverter.model.api

import android.icu.math.BigDecimal
import com.squareup.moshi.Json
import ru.okcode.currencyconverter.model.db.CacheCurrencyRate
import ru.okcode.currencyconverter.model.db.CacheRatesHeader

data class RatesDto(
    @Json(name = "result") val result: String,
    @Json(name = "time_last_update_utc") val timeLastUpdateUtc: String,
    @Json(name = "time_next_update_utc") val timeNextUpdateUtc: String,
    @Json(name = "base_code") val baseCode: String,
    @Json(name = "conversion_rates") val conversionRates: Map<String, Double>
)

fun RatesDto.toCacheRatesHeader() =
    CacheRatesHeader(
        timeLastUpdate = this.timeLastUpdateUtc,
        timeNextUpdate = this.timeNextUpdateUtc,
        baseCode = this.baseCode
    )

fun RatesDto.toCacheCurrencyRatesList(): List<CacheCurrencyRate> =
    this.conversionRates.map {
        CacheCurrencyRate(
            currencyCode = it.key,
            rate = BigDecimal.valueOf(it.value),
            timeLastUpdate = this.timeLastUpdateUtc
        )
    }
