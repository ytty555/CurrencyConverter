package ru.okcode.currencyconverter.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import timber.log.Timber
import java.util.*

@Parcelize
data class ConfiguredCurrency(
    val currencyCode: String,
    val currencyName: String,
    val flagRes: Int?,
    var positionInList: Int,
    var isVisible: Boolean
) : Parcelable {
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

/**
 * Function makes changes by visible currencies list only
 * @param visibleCurrenciesList - currencies list by which changes will be done
 */
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

/**
 * Function 1111111111111
 * @param visibleCurrenciesList - currencies list by which changes will be done
 */
fun List<ConfiguredCurrency>.changeVisibilityAndPositionBy(
    currencyCode: String,
    isVisible: Boolean
) {
    this.forEach {
        if (it.currencyCode == currencyCode) {
            it.isVisible = isVisible
            it.positionInList = 1000
            return@forEach
        }
    }
}
