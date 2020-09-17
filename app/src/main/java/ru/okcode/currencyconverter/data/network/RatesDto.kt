package ru.okcode.currencyconverter.data.network

import android.icu.math.BigDecimal
import com.squareup.moshi.Json
import ru.okcode.currencyconverter.data.db.cache.CacheCurrencyRate
import ru.okcode.currencyconverter.data.db.cache.CacheRatesHeader

data class RatesDto(
    @Json(name = "result") val result: String,
    @Json(name = "time_last_update_utc") val timeLastUpdateUtc: String,
    @Json(name = "time_next_update_utc") val timeNextUpdateUtc: String,
    @Json(name = "time_last_update_unix") val timeLastUpdateUnix: Long,
    @Json(name = "time_next_update_unix") val timeNextUpdateUnix: Long,
    @Json(name = "base_code") val baseCode: String,
    @Json(name = "conversion_rates") val conversionRates: Map<String, Double>
)

fun RatesDto.toCacheRatesHeader() =
    CacheRatesHeader(
        timeLastUpdateUnix = this.timeLastUpdateUnix,
        timeNextUpdateUnix = this.timeNextUpdateUnix,
        baseCode = this.baseCode
    )

fun RatesDto.toCacheCurrencyRatesList(): List<CacheCurrencyRate> =
    this.conversionRates.map {
        CacheCurrencyRate(
            currencyCode = it.key,
            rateToBase = BigDecimal.valueOf(it.value),
            timeLastUpdateUnix = this.timeLastUpdateUnix
        )
    }
