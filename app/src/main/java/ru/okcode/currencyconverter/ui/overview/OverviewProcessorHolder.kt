package ru.okcode.currencyconverter.ui.overview

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.okcode.currencyconverter.data.repository.RawRatesRepository
import ru.okcode.currencyconverter.data.repository.ReadyRepository
import ru.okcode.currencyconverter.data.repository.UpdateStatus
import ru.okcode.currencyconverter.ui.overview.OverviewAction.*
import ru.okcode.currencyconverter.ui.overview.OverviewResult.*
import javax.inject.Inject

class OverviewProcessorHolder @Inject constructor(
    private val readyRepository: ReadyRepository,
    private val rawRatesRepository: RawRatesRepository
) {
    internal val actionProcessor:
            ObservableTransformer<OverviewAction, OverviewResult> =
        ObservableTransformer { actions ->
            actions.publish { shared ->
                Observable.merge(
                    shared.ofType(LoadAllRatesAction::class.java)
                        .compose(loadAllRatesProcessor),
                    shared.ofType(EditCurrencyListAction::class.java)
                        .compose(editCurrencyListProcessor),
                    shared.ofType(ChangeBaseCurrencyAction::class.java)
                        .compose(changeBaseCurrencyProcessor)
                )
                    .mergeWith(
                        shared.ofType(UpdateRawRatesAction::class.java)
                            .compose(updateRawRatesProcessor)
                    )
                    .mergeWith(
                        shared.filter { action ->
                            action !is EditCurrencyListAction
                                    && action !is LoadAllRatesAction
                                    && action !is ChangeBaseCurrencyAction
                                    && action !is UpdateRawRatesAction
                        }.flatMap { action ->
                            Observable.error(
                                IllegalArgumentException("Unknown Action type: $action")
                            )
                        }
                    )
            }
        }

    private val loadAllRatesProcessor:
            ObservableTransformer<LoadAllRatesAction, LoadAllRatesResult> =
        ObservableTransformer { actions ->
            actions.flatMap {
                readyRepository.getReadyRates()
                    .map { rates ->
                        LoadAllRatesResult.Success(rates)
                    }
                    .cast(LoadAllRatesResult::class.java)
                    .onErrorReturn(LoadAllRatesResult::Failure)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread(), true)
                    .startWith(LoadAllRatesResult.Processing)
            }
        }

    private val updateRawRatesProcessor:
            ObservableTransformer<UpdateRawRatesAction, UpdateRawRatesResult> =
        ObservableTransformer { actions ->
            actions.flatMap {
                rawRatesRepository.updateRawRates()
                    .toObservable()
                    .map { updateStatus ->
                        when (updateStatus) {
                            is UpdateStatus.Success -> {
                                UpdateRawRatesResult.Success
                            }
                            is UpdateStatus.NotNeededToUpdate -> {
                                UpdateRawRatesResult.NoNeedUpdate
                            }
                        }
                    }
                    .onErrorReturn(UpdateRawRatesResult::Failure)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(UpdateRawRatesResult.Processing)
            }
        }

    private val editCurrencyListProcessor:
            ObservableTransformer<EditCurrencyListAction, EditCurrencyListResult> =
        ObservableTransformer { actions ->
            actions.map {
                EditCurrencyListResult.Success
            }
                .cast(EditCurrencyListResult::class.java)
                .onErrorReturn(EditCurrencyListResult::Failure)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .startWith(EditCurrencyListResult.Processing)
        }

    private val changeBaseCurrencyProcessor:
            ObservableTransformer<ChangeBaseCurrencyAction, ChangeBaseCurrencyResult> =
        ObservableTransformer { actions ->
            actions.map { action ->
                ChangeBaseCurrencyResult.Success(
                    currencyCode = action.currencyCode,
                    currentCurrencyAmount = action.currentCurrencyAmount
                )
            }
                .cast(ChangeBaseCurrencyResult::class.java)
                .onErrorReturn(ChangeBaseCurrencyResult::Failure)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .startWith(ChangeBaseCurrencyResult.Processing)
        }
}
