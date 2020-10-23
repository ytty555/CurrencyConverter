package ru.okcode.currencyconverter.ui.editcurrencylist

interface EditCurrenciesListListener {
    fun onItemMove(currencyCode: String, priorityPosition: Int)

    fun onItemRemove(currencyCode: String)

}