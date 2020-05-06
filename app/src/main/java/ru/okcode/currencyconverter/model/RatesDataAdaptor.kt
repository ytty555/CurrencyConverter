package ru.okcode.currencyconverter.model

import ru.okcode.currencyconverter.model.db.CurrencyRatesList

class RatesDataAdaptor {
    companion object {
        fun convertToRatesData(inputData: CurrencyRatesList): RatesData {
            val date = inputData.operation.ratesDate
            val baseCurrency = inputData.operation.baseCurrencu
            val rates: List<Rate> = inputData.rates.map { inputRate ->
                Rate(
                    currencyCode = inputRate.currency.currencyCode,
                    flagRes = inputRate.currency.flagRes,
                    fullNameRes = inputRate.currency.fullNameRes,
                    rateToEuro = inputRate.rate.rateToEuro,
                    rateToBase = inputRate.rate.rateToBase
                )
            }
            return RatesData(date, baseCurrency, rates)
        }
    }
}

