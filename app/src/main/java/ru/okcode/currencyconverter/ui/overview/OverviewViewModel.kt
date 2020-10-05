package ru.okcode.currencyconverter.ui.overview

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import ru.okcode.currencyconverter.data.repository.ReadyRepository
import ru.okcode.currencyconverter.mvibase.MviViewModel
import ru.okcode.currencyconverter.ui.Destinations
import ru.okcode.currencyconverter.ui.overview.OverviewAction.*
import ru.okcode.currencyconverter.ui.overview.OverviewIntent.*
import ru.okcode.currencyconverter.ui.overview.OverviewResult.*

class OverviewViewModel @ViewModelInject constructor(
    private val actionProcessorHolder: OverviewProcessorHolder,
    readyRepository: ReadyRepository
) : ViewModel(), MviViewModel<OverviewIntent, OverviewViewState> {

    private val intentsSubject: PublishSubject<OverviewIntent> = PublishSubject.create()
    private val rawRatesChangedSubject =
        BehaviorSubject.create<OverviewViewState>()


    init {
        readyRepository
            .getReadyRates()
            .map {
                OverviewViewState(
                    isLoading = false,
                    switchingTo = null,
                    rates = it,
                    message = null,
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
            is UpdateRawRatesIntent -> UpdateRawRatesAction(intent.nothingToUpdateMessageShow)
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
                    is UpdateRawRatesResult -> when (result) {
                        is UpdateRawRatesResult.Processing -> {
                            previousState.copy(
                                isLoading = true,
                                switchingTo = null,
                                message = null,
                                error = null
                            )
                        }
                        is UpdateRawRatesResult.Success -> {
                            previousState.copy(
                                isLoading = false,
                                switchingTo = null,
                                message = "Rates have updated",
                                error = null
                            )
                        }
                        is UpdateRawRatesResult.NoNeedUpdate -> {
                            previousState.copy(
                                isLoading = false,
                                switchingTo = null,
                                message = if (result.nothingToUpdateMessageShow) "Nothing to update" else null,
                                error = null
                            )
                        }
                        is UpdateRawRatesResult.Failure -> {
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