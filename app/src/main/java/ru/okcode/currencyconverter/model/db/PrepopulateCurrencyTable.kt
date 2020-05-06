package ru.okcode.currencyconverter.model.db

import ru.okcode.currencyconverter.R

class PrepopulateCurrencyTable {

    private val currency1: CurrencyEntity = CurrencyEntity("RUB", R.drawable.ic_rub, R.string.RUB)
    private val currency2: CurrencyEntity = CurrencyEntity("EUR", R.drawable.ic_eur, R.string.EUR)
    private val currency3: CurrencyEntity = CurrencyEntity("USD", R.drawable.ic_usd, R.string.USD)

    val currenciesList = listOf(
        currency1,
        currency2,
        currency3
    )
}