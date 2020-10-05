package ru.okcode.currencyconverter.ui.overview

import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.mvibase.MviResult

sealed class OverviewResult : MviResult {

    sealed class LoadAllRatesResult : OverviewResult() {
        object Processing : LoadAllRatesResult()
        data class Success(val rates: Rates) : LoadAllRatesResult()
        data class Failure(val error: Throwable) : LoadAllRatesResult()
    }

    sealed class UpdateRawRatesResult : OverviewResult() {
        object Processing : UpdateRawRatesResult()
        object Success : UpdateRawRatesResult()
        object NoNeedUpdate : UpdateRawRatesResult()
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