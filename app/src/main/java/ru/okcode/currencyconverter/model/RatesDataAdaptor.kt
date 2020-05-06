package ru.okcode.currencyconverter.model

import ru.okcode.currencyconverter.model.db.CurrencyRatesList
import ru.okcode.currencyconverter.model.db.OperationEntity
import ru.okcode.currencyconverter.model.db.RateEntity

class RatesDataAdaptor {
    companion object {
        fun convertToRatesData(inputData: CurrencyRatesList): RatesData {
            val date = inputData.operation.ratesDate
            val rates: List<Rate> = inputData.rates.map { inputRate ->
                Rate(
                    currencyCode = inputRate.currency.currencyCode,
                    flagRes = inputRate.currency.flagRes,
                    fullNameRes = inputRate.currency.fullNameRes,
                    rateToEuro = inputRate.rate.rateToEuro
                )
            }
            return RatesData(date, rates)
        }
    }
}

