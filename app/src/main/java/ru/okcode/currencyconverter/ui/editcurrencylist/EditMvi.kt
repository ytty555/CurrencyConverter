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
    object SaveCurrenciesToConfigIntent : EditCurrenciesListIntent()
    object AddCurrencyIntent : EditCurrenciesListIntent()
}

/**
 * EditCurrenciesList Action
 */
sealed class EditCurrenciesListAction : MviAction {
    object LoadCurrenciesFromConfigAction : EditCurrenciesListAction()
    data class SaveCurrenciesToConfigAction(val configuredCurrencies: List<ConfiguredCurrency>) :
        EditCurrenciesListAction()

    data class AddCurrencyAction(val currencies: List<ConfiguredCurrency>) :
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
}

/**
 * EditCurrenciesList ViewState
 */
data class EditCurrenciesListViewState(
    val changingPriorityPosition: Boolean,
    val addingCurrencies: Boolean,
    val currencies: List<ConfiguredCurrency>,
    val error: Throwable?
) : MviViewState {
    companion object {
        fun idle(): EditCurrenciesListViewState {
            return EditCurrenciesListViewState(
                changingPriorityPosition = true,
                addingCurrencies = false,
                currencies = emptyList(),
                error = null
            )
        }
    }
}