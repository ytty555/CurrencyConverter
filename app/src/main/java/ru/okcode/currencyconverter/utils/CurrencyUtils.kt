package ru.okcode.currencyconverter.utils

import ru.okcode.currencyconverter.model.CommonRateItem
import ru.okcode.currencyconverter.model.CommonRates
import ru.okcode.currencyconverter.model.CommonRatesDataSet
import ru.okcode.currencyconverter.model.CurrencyEnum
import ru.okcode.currencyconverter.model.api.RatesApiData
import ru.okcode.currencyconverter.model.db.CurrencyRatesList

fun getRateToBase(
    baseCurrencyAmount: Float,
    baseCurrencyRateToEuro: Double,
    currentCurrencyRateToEuro: Double
): Double {
    return currentCurrencyRateToEuro / baseCurrencyRateToEuro * baseCurrencyAmount
}

fun updateBase(
    commonRates: CommonRates,
    baseCurrencyCode: String? = null,
    baseCurrencyRateToEuro: Double? = null,
    baseCurrencyAmount: Float? = null
): CommonRates {
    val dataSet = commonRates.commonRatesDataSet

    if (baseCurrencyCode == null && baseCurrencyRateToEuro == null && baseCurrencyAmount == null) {
        return commonRates
    }

    if (baseCurrencyCode != null) {
        commonRates.commonRatesDataSet.baseCurrencyCode = baseCurrencyCode
    }

    if (baseCurrencyAmount != null) {
        commonRates.commonRatesDataSet.baseCurrencyAmount = baseCurrencyAmount
    }

    if (baseCurrencyRateToEuro != null) {
        commonRates.commonRatesDataSet.baseCurrencyRateToEuro = baseCurrencyRateToEuro
    }

    for (commonRateItem in commonRates.commonRatesList) {
        commonRateItem.apply {
            val baseRateToEuro = commonRates.commonRatesDataSet.baseCurrencyRateToEuro
            val baseAmount = commonRates.commonRatesDataSet.baseCurrencyAmount
            rateToBase = rateToEuro / baseRateToEuro * baseAmount
        }
    }

    return commonRates
}

fun convertToCommonRates(ratesApiData: RatesApiData): CommonRates {
    val ratesDataSet = CommonRatesDataSet(
        actualDate = ratesApiData.date,
        baseCurrencyCode = ratesApiData.baseCurrency,
        baseCurrencyRateToEuro = 1.0,
        baseCurrencyAmount = 1.0F
    )

    val ratesList = ratesApiData.getRatesList().map { rate ->
        CommonRateItem(
            currencyCode = rate.currencyCode,
            flagRes = CurrencyEnum.valueOf(rate.currencyCode).flagRes,
            fullNameRes = CurrencyEnum.valueOf(rate.currencyCode).fullNameRes,
            rateToEuro = rate.rateToEuro,
            rateToBase = getRateToBase(
                baseCurrencyAmount = ratesDataSet.baseCurrencyAmount,
                baseCurrencyRateToEuro = ratesDataSet.baseCurrencyRateToEuro,
                currentCurrencyRateToEuro = rate.rateToEuro
            )
        )
    }

    return CommonRates(ratesDataSet, ratesList)
}

fun convertToCommonRates(currancyRatesList: CurrencyRatesList): CommonRates {
    val dataSet = CommonRatesDataSet(
        actualDate = currancyRatesList.dataSet.actualDate,
        baseCurrencyCode = currancyRatesList.dataSet.baseCurrencyCode,
        baseCurrencyRateToEuro = currancyRatesList.dataSet.baseCurrencyRateToEuro,
        baseCurrencyAmount = currancyRatesList.dataSet.baseCurrencyAmount
    )

    val ratesList = currancyRatesList.rates.map { rateEntity ->
        CommonRateItem(
            currencyCode = rateEntity.currencyCode,
            flagRes = CurrencyEnum.valueOf(rateEntity.currencyCode).flagRes,
            fullNameRes = CurrencyEnum.valueOf(rateEntity.currencyCode).fullNameRes,
            rateToEuro = rateEntity.rateToEuro,
            rateToBase = rateEntity.rateToBase
        )
    }

    return CommonRates(dataSet, ratesList)
}