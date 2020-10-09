package ru.okcode.currencyconverter.ui.overview

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.data.repository.ReadyRepository
import ru.okcode.currencyconverter.mvibase.MviViewModel
import ru.okcode.currencyconverter.ui.overview.OverviewAction.*
import ru.okcode.currencyconverter.ui.overview.OverviewIntent.*
import ru.okcode.currencyconverter.ui.overview.OverviewResult.*
import timber.log.Timber

class OverviewViewModel @ViewModelInject constructor(
    private val actionProcessorHolder: OverviewProcessorHolder,
    private val readyRepository: ReadyRepository,
    private val coordinator: OverviewFlowCoordinator
) : ViewModel(), MviViewModel<OverviewIntent, OverviewViewState> {

    private val intentsSubject: PublishSubject<OverviewIntent> = PublishSubject.create()

    private val disposables = CompositeDisposable()

    private val coordinatorDisposable: Disposable =
        sharedState().subscribe { state ->
            if (state is OverviewViewState.ChangeBaseCurrency) {
                coordinator.showBaseChooser(state.currencyCode, state.currencyAmount)
            } else if (state is OverviewViewState.EditCurrencyList) {
                coordinator.showEditCurrencyList()
            }
        }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    init {
        disposables.add(coordinatorDisposable)
    }

    override fun processIntents(intents: Observable<OverviewIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<OverviewViewState> = sharedState()

    private fun actionFromIntent(intent: OverviewIntent): OverviewAction {
        return when (intent) {
            is InstantiateStateIntent -> InstantiateStateAction(intent.state)
            is ListenCacheAndConfigHaveChangedIntent -> {
                Timber.tag("pizda").d("actionFromIntent ListenCacheAndConfigHaveChangedAction")
                ListenCacheAndConfigHaveChangedAction
            }
            is UpdateRatesIntent -> UpdateRatesAction(intent.nothingToUpdateMessageShow)
            is EditCurrencyListIntent -> EditCurrencyListAction
            is ChangeBaseCurrencyIntent -> ChangeBaseCurrencyAction(
                intent.currencyCode,
                intent.currentAmount
            )
        }
    }

    private fun sharedState(): Observable<OverviewViewState> {
        return intentsSubject
            .map(this::actionFromIntent)
            .compose(actionProcessorHolder.actionProcessor)
            .mergeWith(listenDataChange())
            .scan(OverviewViewState.ReadyData(Rates.idle()), reducer)
            .replay()
            .autoConnect(0)
            .share()
    }

    private fun listenDataChange(): Observable<OverviewResult> {
        return readyRepository.getReadyRates()
            .map { rates ->
                ListenCacheAndConfigHaveChangedResult.Success(rates)
            }
    }

    companion object {
        private val reducer =
            BiFunction { previousState: OverviewViewState, result: OverviewResult ->
                when (result) {
                    is InstantiateStateResult -> when (result) {
                        is InstantiateStateResult.Success -> result.state
                        is InstantiateStateResult.Failure -> {
                            OverviewViewState.Failure(result.error)
                        }
                    }
                    is ListenCacheAndConfigHaveChangedResult -> when (result) {
                        is ListenCacheAndConfigHaveChangedResult.Success -> {
                            Timber.tag("pizda")
                                .d("reducer  ListenCacheAndConfigHaveChangedResult.Success")
                            previousState
                        }
                        is ListenCacheAndConfigHaveChangedResult.Failure -> {
                            Timber.tag("pizda")
                                .d("reducer  ListenCacheAndConfigHaveChangedResult.Failure")
                            OverviewViewState.Failure(result.error)
                        }
                    }
                    is UpdateRatesResult -> when (result) {
                        is UpdateRatesResult.Processing -> {
                            Timber.tag("pizda Update").d("reducer  UpdateRatesResult.Processing")
                            OverviewViewState.Loading
                        }
                        is UpdateRatesResult.Success -> {
                            Timber.tag("pizda Update").d("reducer  UpdateRatesResult.Success")
                            previousState
                        }
                        is UpdateRatesResult.NoNeedUpdate -> {
                            Timber.tag("pizda Update").d("reducer  UpdateRatesResult.NoNeed")
                            previousState
                        }
                        is UpdateRatesResult.Failure -> {
                            Timber.tag("pizda Update").d("reducer  UpdateRatesResult.Failure")
                            OverviewViewState.Failure(result.error)
                        }
                    }
                    is EditCurrencyListResult -> when (result) {
                        is EditCurrencyListResult.Success -> {
                            Timber.d("$result")
                            OverviewViewState.EditCurrencyList
                        }
                        is EditCurrencyListResult.Failure -> {
                            Timber.d("$result")
                            OverviewViewState.Failure(result.error)
                        }
                    }
                    is ChangeBaseCurrencyResult -> when (result) {
                        is ChangeBaseCurrencyResult.Success -> {
                            Timber.d("$result")
                            OverviewViewState.ChangeBaseCurrency(
                                result.currencyCode,
                                result.currentCurrencyAmount
                            )
                        }
                        is ChangeBaseCurrencyResult.Failure -> {
                            Timber.d("$result")
                            OverviewViewState.Failure(result.error)
                        }
                    }
                }
            }
    }
}