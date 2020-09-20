package ru.okcode.currencyconverter.ui.overview

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import ru.okcode.currencyconverter.mvibase.MviViewModel
import ru.okcode.currencyconverter.ui.overview.OverviewAction.*
import ru.okcode.currencyconverter.ui.overview.OverviewIntent.*
import ru.okcode.currencyconverter.ui.overview.OverviewResult.*

class OverviewViewModel @ViewModelInject constructor(
    private val actionProcessorHolder: OverviewProcessorHolder,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(), MviViewModel<OverviewIntent, OverviewViewState> {

    private val intentsSubject: PublishSubject<OverviewIntent> = PublishSubject.create()

    override fun processIntents(intents: Observable<OverviewIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<OverviewViewState> = compose()

    private fun compose(): Observable<OverviewViewState> {
        return intentsSubject
            .map(this::actionFromIntent)
            .compose(actionProcessorHolder.actionProcessor)
            .scan(OverviewViewState.idle(), reducer)
            .distinctUntilChanged()
            .replay()
            .autoConnect(0)
    }

    private fun actionFromIntent(intent: OverviewIntent): OverviewAction {
        return when (intent) {
            is LoadAllRatesIntent -> LoadAllRatesAction
            is EditCurrencyListIntent -> EditCurrencyListAction
            is ChangeBaseCurrencyIntent -> ChangeBaseCurrencyAction(
                intent.currencyCode,
                intent.currentAmount
            )
        }
    }

    companion object {
        private val reducer =
            BiFunction { previousState: OverviewViewState, result: OverviewResult ->
                when (result) {
                    is LoadAllRatesResult -> when (result) {
                        is LoadAllRatesResult.Processing -> {
                            previousState.copy(isLoading = true, error = null)
                        }
                        is LoadAllRatesResult.Success -> {
                            previousState.copy(
                                isLoading = false,
                                rates = result.rates,
                                error = null
                            )
                        }
                        is LoadAllRatesResult.Failure -> {
                            previousState.copy(isLoading = false, error = result.error)
                        }
                    }
                    is EditCurrencyListResult -> when (result) {
                        is EditCurrencyListResult.Processing -> {
                            previousState.copy(isLoading = true, error = null)
                        }
                        is EditCurrencyListResult.Success -> {
                            previousState.copy(
                                isLoading = false,
                                rates = result.rates,
                                error = null
                            )
                        }
                        is EditCurrencyListResult.Failure -> {
                            previousState.copy(isLoading = false, error = result.error)
                        }
                    }
                    is ChangeBaseCurrencyResult -> when (result) {
                        is ChangeBaseCurrencyResult.Processing -> {
                            previousState.copy(isLoading = true, error = null)
                        }
                        is ChangeBaseCurrencyResult.Success -> {
                            previousState.copy(
                                isLoading = false,
                                rates = result.rates,
                                error = null
                            )
                        }
                        is ChangeBaseCurrencyResult.Failure -> {
                            previousState.copy(isLoading = false, error = result.error)
                        }
                    }
                }
            }
    }
}