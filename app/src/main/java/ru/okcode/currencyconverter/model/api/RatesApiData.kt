package ru.okcode.currencyconverter.model.api

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import ru.okcode.currencyconverter.model.CurrencyEnum
import java.util.*
import kotlin.collections.ArrayList

/**
 * Класс итоговых выходных данных из репозитория RatesRepository
 * @param date - дата актуальности курсов валют
 */
data class RatesApiData(
    @SerializedName("date")
    val date: Date,
    @SerializedName("base")
    val baseCurrency: String,
    @SerializedName("rates")
    val jsonRates: JsonObject
) {

    fun getRatesList(): List<Rate> {
        val result = ArrayList<Rate>()
        for (key in jsonRates.keySet()) {
            val currencyEnum = CurrencyEnum.valueOf(key)
            val jsonElement = jsonRates[key]
            result.add(
                Rate(
                    currencyCode = currencyEnum.name,
                    flagRes = currencyEnum.flagRes,
                    fullNameRes = currencyEnum.fullNameRes,
                    rateToEuro = jsonElement.asDouble,
                    rateToBase = jsonElement.asDouble
                )
            )
        }
        return result
    }
}

/**
 * Класс одного конкретного курса валют
 * Содержит описание валюты и курс валюты к базовой валюте (Евро)
 */
data class Rate(
    val currencyCode: String,
    @DrawableRes val flagRes: Int,
    @StringRes val fullNameRes: Int,
    val rateToEuro: Double,
    val rateToBase: Double
)