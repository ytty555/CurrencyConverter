package ru.okcode.currencyconverter.model

import com.google.gson.JsonObject
import ru.okcode.currencyconverter.model.api.RatesApiData
import ru.okcode.currencyconverter.model.db.CurrencyRatesList
import ru.okcode.currencyconverter.model.db.RateEntity

class RatesDataAdaptor {
    companion object {
        fun convertToRatesData(inputData: CurrencyRatesList): RatesApiData {
            val date = inputData.dataSet.actualDate
            val baseCurrency = inputData.dataSet.baseCurrencyCode
            val jsonRates: JsonObject = convertToJson(inputData.rates)
            return RatesApiData(
                date,
                baseCurrency,
                jsonRates
            )
        }

        private fun convertToJson(rates: List<RateEntity>): JsonObject {
            val result = JsonObject()
            for (rateEntity in rates) {
                result.addProperty(rateEntity.currencyCode, rateEntity.rateToEuro)
            }
            return result
        }
    }
}

