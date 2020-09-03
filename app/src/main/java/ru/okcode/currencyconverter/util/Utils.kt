package ru.okcode.currencyconverter.util

import android.icu.text.SimpleDateFormat
import ru.okcode.currencyconverter.model.CurrencyEnum
import java.util.*

fun getFlagRes(currencyCode: String): Int? {
    return try {
        CurrencyEnum.valueOf(currencyCode).flagRes
    } catch (e: IllegalArgumentException) {
        null
    }
}

fun Long.convertUnixToDateString() : String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date = Date(this * 1000)
    return simpleDateFormat.format(date)
}