package ru.okcode.currencyconverter.ui.overview

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import ru.okcode.currencyconverter.data.repository.RawRatesRepository
import ru.okcode.currencyconverter.data.repository.ReadyRepository
import ru.okcode.currencyconverter.mvibase.MviViewModel
import ru.okcode.currencyconverter.ui.Destinations
import ru.okcode.currencyconverter.ui.overview.OverviewAction.*
import ru.okcode.currencyconverter.ui.overview.OverviewIntent.*
import ru.okcode.currencyconverter.ui.overview.OverviewResult.*

class OverviewViewModel @ViewModelInject constructor(
    private val actionProcessorHolder: OverviewProcessorHolder,
    private val rawRatesRepository: RawRatesRepository,
    private val readyRepository: ReadyRepository
) : ViewModel(), MviViewModel<OverviewIntent, OverviewViewState> {

    private val intentsSubject: PublishSubject<OverviewIntent> = PublishSubject.create()
    private val rawRatesChangedSubject =
        BehaviorSubject.create<OverviewViewState>()

    private val ready = readyRepository.getReadyRates()

    init {
        ready
            .doOnNext {
                Log.e(">>> 777 >>>", "$it")
            }
            .map {
                OverviewViewState(
                    isLoading = false,
                    switchingTo = null,
                    rates = it,
                    message = "Rates !!!!!!!!!!!!!!",
                    error = null
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(rawRatesChangedSubject)
    }

    override fun processIntents(intents: Observable<OverviewIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<OverviewViewState> = Observable.merge(
        intentCompose(),
        rawRatesChangedSubject
    )

    private fun intentCompose(): Observable<OverviewViewState> {
        return intentsSubject
            .map(this::actionFromIntent)
            .compose(actionProcessorHolder.actionProcessor)
            .scan(OverviewViewState.idle(), reducer)
            .replay()
            .autoConnect(0)
    }


    private fun actionFromIntent(intent: OverviewIntent): OverviewAction {
        return when (intent) {
            is LoadAllRatesIntent -> LoadAllRatesAction
            is UpdateRawRatesIntent -> UpdateRawRatesAction
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
                            Log.e("qq", "OverviewViewModel LoadAllRatesResult.Processing")
                            previousState.copy(
                                isLoading = true,
                                switchingTo = null,
                                message = null,
                                error = null
                            )
                        }
                        is LoadAllRatesResult.Success -> {
                            Log.e(
                                "qq",
                                "OverviewViewModel LoadAllRatesResult.Success ${result.rates}"
                            )
                            previousState.copy(
                                isLoading = false,
                                switchingTo = null,
                                rates = result.rates,
                                message = null,
                                error = null
                            )
                        }
                        is LoadAllRatesResult.Failure -> {
                            Log.e(
                                "qq",
                                "OverviewViewModel LoadAllRatesResult.Failure ${result.error.localizedMessage}"
                            )
                            previousState.copy(
                                isLoading = false,
                                switchingTo = null,
                                message = null,
                                error = result.error
                            )
                        }
                    }
                    is UpdateRawRatesResult -> when (result) {
                        is UpdateRawRatesResult.Processing -> {
                            Log.e(
                                "qq",
                                "OverviewViewModel UpdateRawRatesResult.Processing"
                            )
                            previousState.copy(
                                isLoading = true,
                                switchingTo = null,
                                message = null,
                                error = null
                            )
                        }
                        is UpdateRawRatesResult.Success -> {
                            Log.e(
                                "qq",
                                "OverviewViewModel UpdateRawRatesResult.Success"
                            )
                            previousState.copy(
                                isLoading = false,
                                switchingTo = null,
                                message = "Rates have updated",
                                error = null
                            )
                        }
                        is UpdateRawRatesResult.NoNeedUpdate -> {
                            Log.e(
                                "qq",
                                "OverviewViewModel UpdateRawRatesResult.NoNeedUpdate"
                            )
                            previousState.copy(
                                isLoading = false,
                                switchingTo = null,
                                message = "Nothing to update",
                                error = null
                            )
                        }
                        is UpdateRawRatesResult.Failure -> {
                            Log.e(
                                "qq",
                                "OverviewViewModel UpdateRawRatesResult.Failure"
                            )
                            previousState.copy(
                                isLoading = false,
                                switchingTo = null,
                                message = null,
                                error = result.error
                            )
                        }
                    }
                    is EditCurrencyListResult -> when (result) {
                        is EditCurrencyListResult.Processing -> {
                            previousState.copy(
                                isLoading = true,
                                switchingTo = null,
                                message = null,
                                error = null
                            )
                        }
                        is EditCurrencyListResult.Success -> {
                            previousState.copy(
                                isLoading = false,
                                switchingTo = Destinations.EditCurrencyListDestination,
                                message = null,
                                error = null
                            )
                        }
                        is EditCurrencyListResult.Failure -> {
                            previousState.copy(
                                isLoading = false,
                                switchingTo = null,
                                message = null,
                                error = result.error
                            )
                        }
                    }
                    is ChangeBaseCurrencyResult -> when (result) {
                        is ChangeBaseCurrencyResult.Processing -> {
                            previousState.copy(
                                isLoading = true,
                                switchingTo = null,
                                message = null,
                                error = null
                            )
                        }
                        is ChangeBaseCurrencyResult.Success -> {
                            previousState.copy(
                                isLoading = false,
                                switchingTo = Destinations.ChangeBaseCurrencyDestination(
                                    currencyCode = result.currencyCode,
                                    currentCurrencyAmount = result.currentCurrencyAmount
                                ),
                                message = null,
                                error = null
                            )
                        }
                        is ChangeBaseCurrencyResult.Failure -> {
                            previousState.copy(
                                isLoading = false,
                                switchingTo = null,
                                message = null,
                                error = result.error
                            )
                        }
                    }
                }
            }
    }
}