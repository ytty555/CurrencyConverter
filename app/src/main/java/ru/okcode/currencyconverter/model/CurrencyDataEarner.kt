package ru.okcode.currencyconverter.model

interface CurrencyDataEarner {
    fun getRates(): List<CurrencyRateDto>
}