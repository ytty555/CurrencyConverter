package ru.okcode.currencyconverter.data.model

import java.util.*

data class ConfiguredCurrency(
    val currencyCode: String,
    val currencyName: String,
    val flagRes: Int?,
    var positionInList: Int,
    var isVisible: Boolean
) {
    companion object {
        val comparator =
            compareBy<ConfiguredCurrency> { it.positionInList }.thenBy { it.currencyCode }
    }
}

fun List<ConfiguredCurrency>.sort(): List<ConfiguredCurrency> {
    Collections.sort(this, ConfiguredCurrency.comparator)
    return this
}

fun List<ConfiguredCurrency>.reindexPriorityPosition() {
    this.forEachIndexed { index, currency ->
        currency.positionInList = index
    }
}

fun List<ConfiguredCurrency>.changeVisibilityAndPositionBy(
    visibleCurrenciesList: List<ConfiguredCurrency>
) {
    this.forEach {
        if (visibleCurrenciesList.contains(it)) {
            it.isVisible = true
            it.positionInList =
                visibleCurrenciesList[visibleCurrenciesList.indexOf(it)].positionInList
        } else {
            it.isVisible = false
            it.positionInList = 1000
        }
    }
}
