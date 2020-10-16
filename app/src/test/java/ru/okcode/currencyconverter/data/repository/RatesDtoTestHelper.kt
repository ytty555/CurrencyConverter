package ru.okcode.currencyconverter.data.repository

import ru.okcode.currencyconverter.data.network.RatesDto
import java.util.*

fun getRatesDtoActualByDate01(): RatesDto {
    return RatesDto(
        result = "success",
        timeLastUpdateUnix = (Date().time / 1000L) - 600_000, // минус 10 минут
        timeNextUpdateUnix = (Date().time / 1000L) + 600_000, // плюс 10 минут
        timeLastUpdateUtc = "123",
        timeNextUpdateUtc = "123",
        baseCode = "EUR",
        conversionRates = getConvertionRates(
            USD = 1.12345f,
            RUB = 85.12345f,
        )
    )
}

fun getRatesDtoActualByDate02(): RatesDto {
    return RatesDto(
        result = "success",
        timeLastUpdateUnix = (Date().time / 1000L) - 600_000, // минус 10 минут
        timeNextUpdateUnix = (Date().time / 1000L) + 600_000, // плюс 10 минут
        timeLastUpdateUtc = "123",
        timeNextUpdateUtc = "123",
        baseCode = "EUR",
        conversionRates = getConvertionRates(
            USD = 1.222f,
            RUB = 85.333f,
        )
    )
}

fun getRatesDtoNOTActualByDate01(): RatesDto {
    return RatesDto(
        result = "success",
        timeLastUpdateUnix = (Date().time / 1000L) - 600_000, // минус 10 минут
        timeNextUpdateUnix = (Date().time / 1000L) - 300_000, // минус 5 минут
        timeLastUpdateUtc = "123",
        timeNextUpdateUtc = "123",
        baseCode = "EUR",
        conversionRates = getConvertionRates(
            USD = 1.222111f,
            RUB = 85.333222f,
        )
    )
}

fun getRatesDtoNOTActualByDate02(): RatesDto {
    return RatesDto(
        result = "success",
        timeLastUpdateUnix = (Date().time / 1000L) - 600_000, // минус 10 минут
        timeNextUpdateUnix = (Date().time / 1000L) - 300_000, // минус 5 минут
        timeLastUpdateUtc = "123",
        timeNextUpdateUtc = "123",
        baseCode = "EUR",
        conversionRates = getConvertionRates(
            USD = 1.33333f,
            RUB = 90.12345f,
        )
    )
}

private fun getConvertionRates(USD: Float, RUB: Float): Map<String, Float> {
    return mutableMapOf(
        "USD" to USD,
        "RUB" to RUB
    )
}
