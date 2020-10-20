package ru.okcode.currencyconverter.data.model

data class ConfiguredCurrency(
    val currencyCode: String,
    val currencyName: String,
    val flagRes: Int?,
    val positionInList: Int,
    val isVisible: Boolean
)