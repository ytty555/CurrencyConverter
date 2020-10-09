package ru.okcode.currencyconverter.ui.overview

import io.reactivex.Observable
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.mvibase.MviResult

sealed class OverviewResult : MviResult {

    sealed class ListenCacheAndConfigHaveChangedResult : OverviewResult() {
        data class Success(val rates: Rates) : ListenCacheAndConfigHaveChangedResult()
        data class Failure(val error: Throwable) : ListenCacheAndConfigHaveChangedResult()
    }

    sealed class InstantiateStateResult: OverviewResult() {
        data class Success(val state: OverviewViewState) : InstantiateStateResult()
        data class Failure(val error: Throwable): InstantiateStateResult()
    }

    sealed class UpdateRatesResult : OverviewResult() {
        object Processing : UpdateRatesResult()
        object Success : UpdateRatesResult()
        data class NoNeedUpdate(val nothingToUpdateMessageShow: Boolean) : UpdateRatesResult()
        data class Failure(val error: Throwable) : UpdateRatesResult()
    }

    sealed class EditCurrencyListResult : OverviewResult() {
        object Success : EditCurrencyListResult()
        data class Failure(val error: Throwable) : EditCurrencyListResult()
    }

    sealed class ChangeBaseCurrencyResult : OverviewResult() {
        data class Success(val currencyCode: String, val currentCurrencyAmount: Float) :
            ChangeBaseCurrencyResult()

        data class Failure(val error: Throwable) : ChangeBaseCurrencyResult()
    }
}