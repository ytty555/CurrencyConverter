package ru.okcode.currencyconverter.model

import java.util.*

class RatesFromApi : CurrencyDataEarner {

    override fun getRates(): List<CurrencyRateDto> {
        // TODO FixIt --- Data must be loaded from a API
        val result: MutableList<CurrencyRateDto> = mutableListOf()
        result.add(CurrencyRateDto(CurrencyEnum.USD, 1.3, Date()))
        result.add(CurrencyRateDto(CurrencyEnum.RUB, 85.5, Date()))
        result.add(CurrencyRateDto(CurrencyEnum.EUR, 1.0, Date()))
        return result
    }

}
