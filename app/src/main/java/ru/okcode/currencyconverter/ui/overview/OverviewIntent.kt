package ru.okcode.currencyconverter.ui.overview

import ru.okcode.currencyconverter.mvibase.MviIntent

sealed class OverviewIntent: MviIntent {
    object LoadAllRatesIntent: OverviewIntent()
    data class ChangeBaseCurrencyIntent(val currencyCode: String): OverviewIntent()
}