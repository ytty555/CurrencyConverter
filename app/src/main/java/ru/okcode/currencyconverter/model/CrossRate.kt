package ru.okcode.currencyconverter.model

class CrossRate(calcCurrency: CurrencyRateDto, baseCurrency: CurrencyRateDto) {
    private var _crossRate = calcCurrency.rateToEUR / baseCurrency.rateToEUR
    val crossRate: Double
        get() = _crossRate
}