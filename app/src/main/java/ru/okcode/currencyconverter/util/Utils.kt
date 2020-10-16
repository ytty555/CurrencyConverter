package ru.okcode.currencyconverter.util

import android.icu.text.SimpleDateFormat
import android.icu.util.Currency
import android.view.View
import ru.okcode.currencyconverter.data.db.cache.CacheCurrencyRate
import ru.okcode.currencyconverter.data.db.cache.CacheHeaderWithRates
import ru.okcode.currencyconverter.data.model.CurrencyFlagsStore
import ru.okcode.currencyconverter.data.network.RatesDto
import java.util.*

private const val CODE_EURO = "EUR"

fun getFlagRes(currencyCode: String): Int? {
    return try {
        CurrencyFlagsStore.valueOf(currencyCode).flagRes
    } catch (e: IllegalArgumentException) {
        null
    }
}

fun Currency.getFlagRes(): Int? {
    return getFlagRes(this.currencyCode)
}

fun Long.convertUnixToDateString() : String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date = Date(this * 1000)
    return simpleDateFormat.format(date)
}

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

fun isActualByDate(startDateUnix: Long, endDateUnix: Long): Boolean {
    val nowDateUnix: Long = System.currentTimeMillis() / 1000L
    return nowDateUnix in (startDateUnix + 1)..endDateUnix
}

fun getRateToEuro(
    currencyRate: CacheCurrencyRate,
    baseCurrencyRateToEuro: Float
): Float {
    return baseCurrencyRateToEuro * currencyRate.rateToBase
}

fun getRateToEuro(
    ratesDto: RatesDto,
    currencyCode: String,
    baseCurrencyRateToEuro: Float = 1f
): Float {
    if (ratesDto.baseCode == currencyCode) {
        return 1f
    }

    val rateToBase: Float? = ratesDto.conversionRates[currencyCode]

    if (rateToBase != null) {
        return baseCurrencyRateToEuro * rateToBase
    } else {
        throw Throwable("Can't find such currency code: $currencyCode")
    }
}

fun getBaseCurrencyRateToEuro(entity: CacheHeaderWithRates): Float {
    if (entity.cacheHeader.baseCode == CODE_EURO) {
        return 1f
    }

    val euroRateToBase =
        entity.rates.filter { CODE_EURO == it.currencyCode }[0].rateToBase

    return 1f / euroRateToBase
}