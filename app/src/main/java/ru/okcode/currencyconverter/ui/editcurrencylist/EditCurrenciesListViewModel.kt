package ru.okcode.currencyconverter.ui.editcurrencylist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.subjects.PublishSubject
import ru.okcode.currencyconverter.data.repository.ConfigRepository
import ru.okcode.currencyconverter.mvibase.MviViewModel
import ru.okcode.currencyconverter.ui.editcurrencylist.EditCurrenciesListAction.*
import ru.okcode.currencyconverter.ui.editcurrencylist.EditCurrenciesListIntent.*
import ru.okcode.currencyconverter.ui.editcurrencylist.EditCurrenciesListResult.*
import timber.log.Timber

class EditCurrenciesListViewModel @ViewModelInject constructor(
    private val processorHolder: EditCurrenciesListProcessorHolder,
    private val configRepository: ConfigRepository
) : ViewModel(),
    MviViewModel<EditCurrenciesListIntent, EditCurrenciesListViewState> {

    private val intentsPublisher =
        PublishSubject.create<EditCurrenciesListIntent>()

    private val configChangedBehavior =
        BehaviorProcessor.create<ListenConfigChangedResult>()

    init {
        listenConfigChanged()
    }

    private fun listenConfigChanged() {
        configRepository.getConfigFlowable()
            .map { config ->
                processorHolder.getConfiguredCurrencyList(config)
                    .map { currencies ->
                        ListenConfigChangedResult.Success(currencies)
                    }
            }
            .cast(ListenConfigChangedResult::class.java)
            .onErrorReturn(ListenConfigChangedResult::Failure)
            .doOnNext { Timber.d("config changed") }
            .subscribe(configChangedBehavior)
    }

    override fun processIntents(intents: Observable<EditCurrenciesListIntent>) {
        intents.subscribe(intentsPublisher)
    }

    override fun states(): Observable<EditCurrenciesListViewState> =
        intentsPublisher
            .map(this::actionFromIntent)
            .compose(processorHolder.actionProcessor)
            .mergeWith(configChangedBehavior.toObservable())
            .scan(EditCurrenciesListViewState.idle(), reducer)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { Timber.d("new EditCurrenciesListViewState $it") }


    private fun actionFromIntent(intent: EditCurrenciesListIntent): EditCurrenciesListAction =
        when (intent) {
            is AddCurrencyIntent -> AddCurrencyAction
            is MoveCurrencyIntent -> MoveCurrencyAction
            is RemoveCurrencyIntent -> RemoveCurrencyAction
        }

    companion object {
        private val reducer: BiFunction<
                EditCurrenciesListViewState,
                EditCurrenciesListResult,
                EditCurrenciesListViewState> =
            BiFunction { previousState: EditCurrenciesListViewState, result: EditCurrenciesListResult ->
                when (result) {
                    is ListenConfigChangedResult -> when (result) {
                        is ListenConfigChangedResult.Success -> {
                            previousState.copy(
                                currencies = result.currencies,
                                error = null
                            )
                        }
                        is ListenConfigChangedResult.Failure -> {
                            previousState.copy(
                                error = result.error
                            )
                        }
                    }
                    is AddCurrencyResult -> when (result) {
                        is AddCurrencyResult.Success -> {
                            previousState.copy(
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
                            previousState.copy(
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