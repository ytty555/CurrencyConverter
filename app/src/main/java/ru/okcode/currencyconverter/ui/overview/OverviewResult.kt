package ru.okcode.currencyconverter.ui.overview

import ru.okcode.currencyconverter.mvibase.MviResult

sealed class OverviewResult : MviResult {

    sealed class UpdateRawRatesResult : OverviewResult() {
        object Processing : UpdateRawRatesResult()
        object Success : UpdateRawRatesResult()
        data class NoNeedUpdate(val nothingToUpdateMessageShow: Boolean) : UpdateRawRatesResult()
        data class Failure(val error: Throwable) : UpdateRawRatesResult()
    }

    sealed class EditCurrencyListResult : OverviewResult() {
        object Processing : EditCurrencyListResult()
        object Success : EditCurrencyListResult()
        data class Failure(val error: Throwable) : EditCurrencyListResult()
    }

    sealed class ChangeBaseCurrencyResult : OverviewResult() {
        object Processing : ChangeBaseCurrencyResult()
        data class Success(val currencyCode: String, val currentCurrencyAmount: Float) :
            ChangeBaseCurrencyResult()

        data class Failure(val error: Throwable) : ChangeBaseCurrencyResult()
    }
}