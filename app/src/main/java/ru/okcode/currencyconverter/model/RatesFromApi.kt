package ru.okcode.currencyconverter.model

import java.util.*

class RatesFromApi : CurrencyDataEarner {

    override fun getRates(): List<CurrencyRateDto> {
        // TODO FixIt --- Data must be loaded from a API
        val result: MutableList<CurrencyRateDto> = mutableListOf()
        val baseCurrency = CurrencyEnum.EUR
        result.add(CurrencyRateDto(CurrencyEnum.USD, baseCurrency,1.3, 1.3, Date()))
        result.add(CurrencyRateDto(CurrencyEnum.RUB, baseCurrency,85.5, 85.5, Date()))
        result.add(CurrencyRateDto(CurrencyEnum.EUR, baseCurrency,1.0, 1.0, Date()))
        return result
    }

}
