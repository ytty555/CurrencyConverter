package ru.okcode.currencyconverter.data.model

data class ConfiguredCurrency(
    val currencyCode: String,
    val currencyName: String,
    val flagRes: Int?,
    var positionInList: Int,
    val isVisible: Boolean
)
