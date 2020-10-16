package ru.okcode.currencyconverter.ui.overview

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import ru.okcode.currencyconverter.data.repository.ReadyRepository
import ru.okcode.currencyconverter.mvibase.MviViewModel
import ru.okcode.currencyconverter.ui.overview.OverviewAction.*
import ru.okcode.currencyconverter.ui.overview.OverviewIntent.*
import ru.okcode.currencyconverter.ui.overview.OverviewResult.*
import timber.log.Timber

class OverviewViewModel @ViewModelInject constructor(
    private val actionProcessorHolder: OverviewProcessorHolder,
    private val readyRepository: ReadyRepository,
    private val navigator: OverviewNavigator
) : ViewModel(), MviViewModel<OverviewIntent, OverviewViewState> {

    private val intentsSubject: PublishSubject<OverviewIntent> = PublishSubject.create()
    private val dataChangeBehavior = BehaviorSubject.create<ListenCacheAndConfigHaveChangedResult>()

    private val disposables = CompositeDisposable()

    override fun onCleared() {
        Timber.d("onCleared()")
        super.onCleared()
        disposables.clear()
    }

    init {
        Timber.d("init{}")

        val coordinatorDisposable: Disposable =
            sharedState().subscribe { state ->
                if (state.changeBaseCurrency != null) {
                    val currencyCode = state.changeBaseCurrency.first
                    val currencyAmount = state.changeBaseCurrency.second
                    navigator.showBaseChooser(currencyCode, currencyAmount)
                } else if (state.editCurrencyList) {
                    navigator.showEditCurrencyList()
                }
            }

        disposables.add(coordinatorDisposable)

        listenToDataChange()
    }

    override fun processIntents(intents: Observable<OverviewIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<OverviewViewState> = sharedState()

    private fun actionFromIntent(intent: OverviewIntent): OverviewAction {
        return when (intent) {
            is InstantiateStateIntent -> InstantiateStateAction(intent.state)
            is ListenCacheAndConfigHaveChangedIntent -> {
                ListenCacheAndConfigHaveChangedAction
            }
            is UpdateRatesIntent -> UpdateRatesAction
            is EditCurrencyListIntent -> EditCurrencyListAction
            is ChangeBaseCurrencyIntent -> ChangeBaseCurrencyAction(
                intent.currencyCode,
                intent.currentAmount
            )
        }
    }

    private fun sharedState(): Observable<OverviewViewState> {
        return intentsSubject
            .doOnNext{
                Timber.i("--- action ---")
            }
            .map(this::actionFromIntent)
            .compose(actionProcessorHolder.actionProcessor)
            .mergeWith(dataChangeBehavior)
            .scan(OverviewViewState.idle(), reducer)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                Timber.d("intentsSubject $it")
            }
    }

    private fun listenToDataChange() {
        readyRepository.getReadyRates()
            .map { rates ->
                ListenCacheAndConfigHaveChangedResult.Success(rates)
            }
            .distinctUntilChanged()
            .doOnNext {
                Timber.d("Data has changed")
            }
            .subscribe(dataChangeBehavior)

    }

    companion object {
        private val reducer =
            BiFunction { previousState: OverviewViewState, result: OverviewResult ->
                when (result) {
                    is InstantiateStateResult -> when (result) {
                        is InstantiateStateResult.Success -> {
                            Timber.i("InstantiateStateResult.Success")
                            result.state
                        }
                        is InstantiateStateResult.Failure -> {
                            Timber.i("InstantiateStateResult.Failure")
                            previousState.copy(
                                isLoading = false,
                                error = result.error,
                                changeBaseCurrency = null,
                                editCurrencyList = false
                            )
                        }
                    }
                    is ListenCacheAndConfigHaveChangedResult -> when (result) {
                        is ListenCacheAndConfigHaveChangedResult.Success -> {
                            Timber.i("ListenCacheAndConfigHaveChangedResult.Success")
                            previousState.copy(
                                isLoading = false,
                                readyData = result.rates,
                                error = null,
                                changeBaseCurrency = null,
                                editCurrencyList = false
                            )
                        }
                        is ListenCacheAndConfigHaveChangedResult.Failure -> {
                            Timber.i("ListenCacheAndConfigHaveChangedResult.Failure")
                            previousState.copy(
                                isLoading = false,
                                error = result.error,
                                changeBaseCurrency = null,
                                editCurrencyList = false
                            )
                        }
                    }
                    is UpdateRatesResult -> when (result) {
                        is UpdateRatesResult.Processing -> {
                            Timber.i("UpdateRatesResult.Processing")
                            previousState.copy(
                                isLoading = true,
                                error = null,
                                changeBaseCurrency = null,
                                editCurrencyList = false
                            )
                        }
                        is UpdateRatesResult.Success -> {
                            Timber.i("UpdateRatesResult.Success")
                            previousState.copy(
                                isLoading = false,
                                error = null,
                                changeBaseCurrency = null,
                                editCurrencyList = false
                            )
                        }
                        is UpdateRatesResult.NoNeedUpdate -> {
                            Timber.i("UpdateRatesResult.NoNeedUpdate")
                            previousState.copy(
                                isLoading = false,
                                error = null,
                                changeBaseCurrency = null,
                                editCurrencyList = false
                            )
                        }
                        is UpdateRatesResult.Failure -> {
                            Timber.i("UpdateRatesResult.Failure")
                            previousState.copy(
                                isLoading = false,
                                error = result.error,
                                changeBaseCurrency = null,
                                editCurrencyList = false
                            )
                        }
                    }
                    is EditCurrencyListResult -> when (result) {
                        is EditCurrencyListResult.Success -> {
                            Timber.i("EditCurrencyListResult.Success")
                            previousState.copy(
                                isLoading = false,
                                error = null,
                                changeBaseCurrency = null,
                                editCurrencyList = true
                            )
                        }
                        is EditCurrencyListResult.Failure -> {
                            Timber.i("EditCurrencyListResult.Failure")
                            previousState.copy(
                                isLoading = false,
                                error = result.error,
                                changeBaseCurrency = null,
                                editCurrencyList = false
                            )
                        }
                    }
                    is ChangeBaseCurrencyResult -> when (result) {
                        is ChangeBaseCurrencyResult.Success -> {
                            Timber.i("ChangeBaseCurrencyResult.Success")
                            previousState.copy(
                                isLoading = false,
                                error = null,
                                changeBaseCurrency = Pair(
                                    result.currencyCode,
                                    result.currentCurrencyAmount
                                ),
                                editCurrencyList = false
                            )
                        }
                        is ChangeBaseCurrencyResult.Failure -> {
                            Timber.i("ChangeBaseCurrencyResult.Failure")
                            previousState.copy(
                                isLoading = false,
                                error = result.error,
                                changeBaseCurrency = null,
                                editCurrencyList = false
                            )
                        }
                    }
                }
            }
    }
}