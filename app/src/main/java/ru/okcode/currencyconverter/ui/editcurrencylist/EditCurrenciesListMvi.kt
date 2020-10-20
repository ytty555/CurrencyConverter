package ru.okcode.currencyconverter.ui.editcurrencylist

import ru.okcode.currencyconverter.data.model.ConfiguredCurrency
import ru.okcode.currencyconverter.mvibase.MviAction
import ru.okcode.currencyconverter.mvibase.MviIntent
import ru.okcode.currencyconverter.mvibase.MviResult
import ru.okcode.currencyconverter.mvibase.MviViewState

/**
 * EditCurrenciesList Intent
 */
sealed class EditCurrenciesListIntent : MviIntent {
    object LoadCurrenciesFromConfigIntent : EditCurrenciesListIntent()
    object SaveCurrenciesToConfigIntent: EditCurrenciesListIntent()
    object AddCurrencyIntent : EditCurrenciesListIntent()
    object RemoveCurrencyIntent : EditCurrenciesListIntent()
    object MoveCurrencyIntent : EditCurrenciesListIntent()
}

/**
 * EditCurrenciesList Action
 */
sealed class EditCurrenciesListAction : MviAction {
    object LoadCurrenciesFromConfigAction : EditCurrenciesListAction()
    object SaveCurrenciesToConfigAction : EditCurrenciesListAction()
    object AddCurrencyAction : EditCurrenciesListAction()
    object RemoveCurrencyAction : EditCurrenciesListAction()
    object MoveCurrencyAction : EditCurrenciesListAction()
}

/**
 * EditCurrenciesList Result
 */
sealed class EditCurrenciesListResult : MviResult {

    sealed class LoadCurrenciesFromConfigResult : EditCurrenciesListResult() {
        data class Success(val currencies: List<ConfiguredCurrency>) :
            LoadCurrenciesFromConfigResult()

        data class Failure(val error: Throwable) : LoadCurrenciesFromConfigResult()
    }

    sealed class SaveCurrenciesToConfigResult : EditCurrenciesListResult() {
        object Success : SaveCurrenciesToConfigResult()
        data class Failure(val error: Throwable) : SaveCurrenciesToConfigResult()
    }

    sealed class AddCurrencyResult : EditCurrenciesListResult() {
        object Success : AddCurrencyResult()
        data class Failure(val error: Throwable) : AddCurrencyResult()
    }

    sealed class RemoveCurrencyResult : EditCurrenciesListResult() {
        object Success : RemoveCurrencyResult()
        data class Failure(val error: Throwable) : RemoveCurrencyResult()
    }

    sealed class MoveCurrencyResult : EditCurrenciesListResult() {
        object Success : MoveCurrencyResult()
        data class Failure(val error: Throwable) : MoveCurrencyResult()
    }
}

/**
 * EditCurrenciesList ViewState
 */
data class EditCurrenciesListViewState(
    val currencies: List<ConfiguredCurrency>,
    val error: Throwable?
) : MviViewState {
    companion object {
        fun idle(): EditCurrenciesListViewState {
            return EditCurrenciesListViewState(emptyList(), null)
        }
    }
}