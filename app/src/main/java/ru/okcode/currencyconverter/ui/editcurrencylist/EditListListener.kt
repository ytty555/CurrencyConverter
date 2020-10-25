package ru.okcode.currencyconverter.ui.editcurrencylist

import ru.okcode.currencyconverter.data.model.ConfiguredCurrency

interface EditListListener {
    fun onChangeCurrenciesList(visibleCurrencies: List<ConfiguredCurrency>)
}