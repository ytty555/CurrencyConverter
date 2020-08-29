package ru.okcode.currencyconverter.util

import ru.okcode.currencyconverter.model.CurrencyEnum

fun getFlagRes(currencyCode: String): Int? {
    return try {
        CurrencyEnum.valueOf(currencyCode).flagRes
    } catch (e: IllegalArgumentException) {
        null
    }
}