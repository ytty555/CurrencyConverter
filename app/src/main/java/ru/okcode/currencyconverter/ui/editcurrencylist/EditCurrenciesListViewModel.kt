package ru.okcode.currencyconverter.ui.editcurrencylist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import ru.okcode.currencyconverter.mvibase.MviViewModel
import ru.okcode.currencyconverter.ui.editcurrencylist.EditCurrenciesListAction.*
import ru.okcode.currencyconverter.ui.editcurrencylist.EditCurrenciesListIntent.*
import ru.okcode.currencyconverter.ui.editcurrencylist.EditCurrenciesListResult.*
import timber.log.Timber

class EditCurrenciesListViewModel @ViewModelInject constructor(
    private val processorHolder: EditCurrenciesListProcessorHolder,
) : ViewModel(),
    MviViewModel<EditCurrenciesListIntent, EditCurrenciesListViewState> {

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
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { Timber.d("new EditCurrenciesListViewState $it") }


    private fun actionFromIntent(intent: EditCurrenciesListIntent): EditCurrenciesListAction =
        when (intent) {
            is LoadCurrenciesFromConfigIntent -> LoadCurrenciesFromConfigAction
            is SaveCurrenciesToConfigIntent -> SaveCurrenciesToConfigAction(intent.configuredCurrencies)
            is AddCurrencyIntent -> AddCurrencyAction(intent.configuredCurrencies)
            is MoveCurrencyIntent -> MoveCurrencyAction(
                intent.currencyCode,
                intent.priorityPosition
            )
            is RemoveCurrencyIntent -> RemoveCurrencyAction(intent.configuredCurrencies)
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
                    is MoveCurrencyResult -> when (result) {
                        is MoveCurrencyResult.Success -> {
                            val newCurrencies =
                                previousState.currencies.toMutableList()
                            for (currency in newCurrencies) {
                                if (currency.currencyCode == result.currencyCode) {
                                    currency.positionInList = result.priorityPosition
                                }
                            }
                            previousState.copy(
                                currencies = newCurrencies,
                                error = null
                            )
                        }
                        is MoveCurrencyResult.Failure -> {
                            previousState.copy(
                                error = result.error
                            )
                        }
                    }
                    is RemoveCurrencyResult -> when (result) {
                        is RemoveCurrencyResult.Success -> {
                            previousState.copy(
                                currencies = result.currencies,
                                error = null
                            )
                        }
                        is RemoveCurrencyResult.Failure -> {
                            previousState.copy(
                                error = result.error
                            )
                        }
                    }
                }
            }

    }
}