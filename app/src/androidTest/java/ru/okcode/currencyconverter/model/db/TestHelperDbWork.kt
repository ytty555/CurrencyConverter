package ru.okcode.currencyconverter.model.db

import ru.okcode.currencyconverter.model.CommonRateItem
import ru.okcode.currencyconverter.model.CommonRates
import ru.okcode.currencyconverter.model.CommonRatesDataSet
import ru.okcode.currencyconverter.model.CurrencyEnum
import java.util.*

class TestHelperDbWork {

    fun createTestCommonRatesBaseEuro(): CommonRates {
        val dataSet = CommonRatesDataSet(
            actualDate = Date(),
            baseCurrencyCode = "EUR",
            baseCurrencyRateToEuro = 1.0,
            baseCurrencyAmount = 1.0f
        )

        val ratesList: List<CommonRateItem> = createTestRatesList()

        return CommonRates(dataSet, ratesList)
    }

    private fun createTestRatesList(): List<CommonRateItem> {
        val resultList = ArrayList<CommonRateItem>(3)
        resultList.add(createTestRateBaseEuro("RUB"))
        resultList.add(createTestRateBaseEuro("EUR"))
        resultList.add(createTestRateBaseEuro("USD"))
        return resultList
    }

    private fun createTestRateBaseEuro(currencyCode: String): CommonRateItem {
        val rateToEuro: Double = when (currencyCode) {
            "RUB" -> 77.5
            "EUR" -> 1.0
            "USD" -> 1.32
            else -> 555.555
        }

        return CommonRateItem(
            currencyCode = currencyCode,
            flagRes = CurrencyEnum.valueOf(currencyCode).flagRes,
            fullNameRes = CurrencyEnum.valueOf(currencyCode).fullNameRes,
            rateToEuro = rateToEuro,
            rateToBase = rateToEuro
        )
    }
}