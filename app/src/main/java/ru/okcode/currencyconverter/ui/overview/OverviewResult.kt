package ru.okcode.currencyconverter.ui.overview

import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.mvibase.MviResult

sealed class OverviewResult : MviResult {

    sealed class LoadAllRatesResult : OverviewResult() {
        object Processing : LoadAllRatesResult()
        data class Success(val rates: Rates) : LoadAllRatesResult()
        class Failure(val error: Throwable) : LoadAllRatesResult()
    }

    sealed class EditCurrencyListResult : OverviewResult() {
        object Processing : EditCurrencyListResult()
        data class Success(val rates: Rates) : EditCurrencyListResult()
        data class Failure(val error: Throwable) : EditCurrencyListResult()
    }

    sealed class ChangeBaseCurrencyResult : OverviewResult() {
        object Processing : ChangeBaseCurrencyResult()
        data class Success(val rates: Rates) : ChangeBaseCurrencyResult()
        data class Failure(val error: Throwable) : ChangeBaseCurrencyResult()
    }
}