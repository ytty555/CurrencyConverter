package ru.okcode.currencyconverter.ui.editcurrencylist

import ru.okcode.currencyconverter.data.model.ConfiguredCurrency
import ru.okcode.currencyconverter.mvibase.MviAction
import ru.okcode.currencyconverter.mvibase.MviIntent
import ru.okcode.currencyconverter.mvibase.MviResult
import ru.okcode.currencyconverter.mvibase.MviViewState

/**
 * EditCurrenciesList Intent
 */
sealed class EditCurrenciesListIntent: MviIntent {
    object AddCurrencyIntent: EditCurrenciesListIntent()
    object RemoveCurrencyIntent: EditCurrenciesListIntent()
    object MoveCurrencyIntent: EditCurrenciesListIntent()
}

/**
 * EditCurrenciesList Action
 */
sealed class EditCurrenciesListAction: MviAction {
    object AddCurrencyAction: EditCurrenciesListAction()
    object RemoveCurrencyAction: EditCurrenciesListAction()
    object MoveCurrencyAction: EditCurrenciesListAction()
}

/**
 * EditCurrenciesList Result
 */
sealed class EditCurrenciesListResult: MviResult {

    sealed class AddCurrencyResult: EditCurrenciesListResult() {
        object Success: AddCurrencyResult()
        data class Failure(val error: Throwable): AddCurrencyResult()
    }

    sealed class RemoveCurrencyResult: EditCurrenciesListResult() {
        object Success: RemoveCurrencyResult()
        data class Failure(val error: Throwable): RemoveCurrencyResult()
    }

    sealed class MoveCurrencyResult: EditCurrenciesListResult() {
        object Success: MoveCurrencyResult()
        data class Failure(val error: Throwable): MoveCurrencyResult()
    }

    sealed class ListenConfigChangedResult: EditCurrenciesListResult() {
        data class Success(val currencies: List<ConfiguredCurrency>): ListenConfigChangedResult()
        data class Failure(val error: Throwable): ListenConfigChangedResult()
    }
}

/**
 * EditCurrenciesList ViewState
 */
data class EditCurrenciesListViewState(
    val currencies: List<ConfiguredCurrency>,
    val error: Throwable?
): MviViewState {
    companion object {
        fun idle(): EditCurrenciesListViewState {
            return EditCurrenciesListViewState(emptyList(), null)
        }
    }
}