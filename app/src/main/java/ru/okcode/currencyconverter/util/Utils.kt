package ru.okcode.currencyconverter.util

import android.icu.text.SimpleDateFormat
import android.icu.util.Currency
import android.view.View
import ru.okcode.currencyconverter.data.model.CurrencyFlagsStore
import java.time.Instant
import java.util.*

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