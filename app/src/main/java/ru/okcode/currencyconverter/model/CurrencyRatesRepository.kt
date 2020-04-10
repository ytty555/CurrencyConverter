package ru.okcode.currencyconverter.model

class CurrencyRatesRepository {
    private var dataEarner: CurrencyDataEarner = RatesFromApi()

    val rates: List<CurrencyRateDto>
        get() = dataEarner.getRates()
}