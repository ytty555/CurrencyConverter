package ru.okcode.currencyconverter.model

import java.util.*

data class CurrencyRateDto(val currency: CurrencyEnum, var rateToEUR: Double, var rateDate: Date)