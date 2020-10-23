package ru.okcode.currencyconverter.ui.editcurrencylist

import ru.okcode.currencyconverter.data.model.ConfiguredCurrency
import ru.okcode.currencyconverter.mvibase.MviAction
import ru.okcode.currencyconverter.mvibase.MviIntent
import ru.okcode.currencyconverter.mvibase.MviResult
import ru.okcode.currencyconverter.mvibase.MviViewState
import java.text.FieldPosition

/**
 * EditCurrenciesList Intent
 */
sealed class EditCurrenciesListIntent : MviIntent {
    object LoadCurrenciesFromConfigIntent : EditCurrenciesListIntent()
    data class SaveCurrenciesToConfigIntent(val configuredCurrencies: List<ConfiguredCurrency>) :
        EditCurrenciesListIntent()

    data class AddCurrencyIntent(val configuredCurrencies: List<ConfiguredCurrency>) :
        EditCurrenciesListIntent()

    data class RemoveCurrencyIntent(val configuredCurrencies: List<ConfiguredCurrency>) :
        EditCurrenciesListIntent()

    data class MoveCurrencyIntent(val currencyCode: String, val priorityPosition: Int) :
        EditCurrenciesListIntent()
}

/**
 * EditCurrenciesList Action
 */
sealed class EditCurrenciesListAction : MviAction {
    object LoadCurrenciesFromConfigAction : EditCurrenciesListAction()
    data class SaveCurrenciesToConfigAction(val configuredCurrencies: List<ConfiguredCurrency>) :
        EditCurrenciesListAction()

    data class AddCurrencyAction(val configuredCurrencies: List<ConfiguredCurrency>) :
        EditCurrenciesListAction()

    data class RemoveCurrencyAction(val configuredCurrencies: List<ConfiguredCurrency>) :
        EditCurrenciesListAction()

    data class MoveCurrencyAction(val currencyCode: String, val priorityPosition: Int) :
        EditCurrenciesListAction()
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
        data class Success(val currencies: List<ConfiguredCurrency>) : AddCurrencyResult()
        data class Failure(val error: Throwable) : AddCurrencyResult()
    }

    sealed class RemoveCurrencyResult : EditCurrenciesListResult() {
        data class Success(val currencies: List<ConfiguredCurrency>) : RemoveCurrencyResult()
        data class Failure(val error: Throwable) : RemoveCurrencyResult()
    }

    sealed class MoveCurrencyResult : EditCurrenciesListResult() {
        data class Success(val currencyCode: String, val priorityPosition: Int) : MoveCurrencyResult()
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