package ru.okcode.currencyconverter.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import java.util.*

/**
 * Класс итоговых выходных данных из репозитория RatesRepository
 * @param date - дата актуальности курсов валют
 * @param rates - список курсов валют
 */
data class RatesData(
    val date: Date,
    val rates: List<Rate>
) {

}

/**
 * Класс одного конкретного курса валют
 * Содержит описание валюты и курс валюты к базовой валюте (Евро)
 */
data class Rate(
    val currencyCode: String,
    @DrawableRes val flagRes: Int,
    @StringRes val fullNameRes: Int,
    val rateToEuro: Double
)