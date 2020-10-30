package ru.okcode.currencyconverter.ui.editcurrencylist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import ru.okcode.currencyconverter.data.model.ConfiguredCurrency
import ru.okcode.currencyconverter.mvibase.MviViewModel
import ru.okcode.currencyconverter.ui.editcurrencylist.EditCurrenciesListAction.*
import ru.okcode.currencyconverter.ui.editcurrencylist.EditCurrenciesListIntent.*
import ru.okcode.currencyconverter.ui.editcurrencylist.EditCurrenciesListResult.*
import timber.log.Timber

class EditViewModel @ViewModelInject constructor(
    private val processorHolder: EditProcessorHolder,
) : ViewModel(),
    MviViewModel<EditCurrenciesListIntent, EditCurrenciesListViewState> {

    // Temp result while editing
    var tempCurrenciesWhileEditing: List<ConfiguredCurrency> = emptyList()

    private val intentsPublisher =
        PublishSubject.create<EditCurrenciesListIntent>()


    override fun processIntents(intents: Observable<EditCurrenciesListIntent>) {
        intents.subscribe(intentsPublisher)
    }

    override fun states(): Observable<EditCurrenciesListViewState> =
        intentsPublisher
            .map(this::actionFromIntent)
            .compose(processorHolder.actionProcessor)
            .scan(EditCurrenciesListViewState.idle(), reducer)
            .filter {
                it.currencies.isNotEmpty()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                if (it.error == null) {
                    tempCurrenciesWhileEditing = it.currencies
                }
            }


    private fun actionFromIntent(intent: EditCurrenciesListIntent): EditCurrenciesListAction =
        when (intent) {
            is LoadCurrenciesFromConfigIntent -> LoadCurrenciesFromConfigAction
            is SaveCurrenciesToConfigIntent -> SaveCurrenciesToConfigAction(intent.configuredCurrencies)
            is AddCurrencyIntent -> AddCurrencyAction(intent.configuredCurrencies)
        }

    companion object {
        private val reducer: BiFunction<
                EditCurrenciesListViewState,
                EditCurrenciesListResult,
                EditCurrenciesListViewState> =
            BiFunction { previousState: EditCurrenciesListViewState, result: EditCurrenciesListResult ->
                when (result) {
                    is LoadCurrenciesFromConfigResult -> when (result) {
                        is LoadCurrenciesFromConfigResult.Success -> {
                            previousState.copy(
                                currencies = result.currencies,
                                error = null
                            )
                        }
                        is LoadCurrenciesFromConfigResult.Failure -> {
                            previousState.copy(
                                error = result.error
                            )
                        }
                    }
                    is SaveCurrenciesToConfigResult -> when (result) {
                        is SaveCurrenciesToConfigResult.Success -> {
                            Timber.d("ppp SaveCurrenciesToConfigResult.Success")
                            previousState.copy(
                                error = null
                            )
                        }
                        is SaveCurrenciesToConfigResult.Failure -> {
                            previousState.copy(
                                error = result.error
                            )
                        }
                    }
                    is AddCurrencyResult -> when (result) {
                        is AddCurrencyResult.Success -> {
                            previousState.copy(
                                currencies = result.currencies,
                                error = null
                            )
                        }
                        is AddCurrencyResult.Failure -> {
                            previousState.copy(
                                error = result.error
                            )
                        }
                    }

                }
            }

    }
}