package ru.okcode.currencyconverter.model

import com.google.gson.JsonObject
import ru.okcode.currencyconverter.model.api.Rate
import ru.okcode.currencyconverter.model.api.RatesData
import ru.okcode.currencyconverter.model.db.CurrencyRatesList
import ru.okcode.currencyconverter.model.db.RateEntity

class RatesDataAdaptor {
    companion object {
        fun convertToRatesData(inputData: CurrencyRatesList): RatesData {
            val date = inputData.operation.ratesDate
            val baseCurrency = inputData.operation.baseCurrency
            val jsonRates: JsonObject = convertToJson(inputData.rates)
            return RatesData(
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

