package ru.okcode.currencyconverter.ui.overview

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.okcode.currencyconverter.data.repository.RawRatesRepository
import ru.okcode.currencyconverter.data.repository.UpdateStatus
import ru.okcode.currencyconverter.ui.overview.OverviewAction.*
import ru.okcode.currencyconverter.ui.overview.OverviewResult.*
import javax.inject.Inject

class OverviewProcessorHolder @Inject constructor(
    private val rawRatesRepository: RawRatesRepository
) {
    internal val actionProcessor:
            ObservableTransformer<OverviewAction, OverviewResult> =
        ObservableTransformer { actions ->
            actions.publish { shared ->
                Observable.merge(
                    shared.ofType(EditCurrencyListAction::class.java)
                        .compose(editCurrencyListProcessor),
                    shared.ofType(ChangeBaseCurrencyAction::class.java)
                        .compose(changeBaseCurrencyProcessor),
                    shared.ofType(UpdateRatesAction::class.java)
                        .compose(updateRatesProcessor),
                    shared.ofType(InstantiateStateAction::class.java)
                        .compose(instantiateLastStateProcessor)
                )
                    .mergeWith(
                        shared.filter { action ->
                            action !is EditCurrencyListAction
                                    && action !is ChangeBaseCurrencyAction
                                    && action !is UpdateRatesAction
                                    && action !is ListenCacheAndConfigHaveChangedAction
                                    && action !is InstantiateStateAction
                        }.flatMap { action ->
                            Observable.error(
                                IllegalArgumentException("Unknown Action type: $action")
                            )
                        }
                    )
            }
        }

    private val instantiateLastStateProcessor:
            ObservableTransformer<InstantiateStateAction, InstantiateStateResult> =
        ObservableTransformer { actions ->
            actions.map {
                InstantiateStateResult.Success(it.state)
            }
                .cast(InstantiateStateResult::class.java)
                .onErrorReturn(InstantiateStateResult::Failure)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    private val updateRatesProcessor:
            ObservableTransformer<UpdateRatesAction, UpdateRatesResult> =
        ObservableTransformer { actions ->
            actions.flatMap {
                rawRatesRepository.updateRawRates()
                    .toObservable()
                    .map { updateStatus ->
                        when (updateStatus) {
                            is UpdateStatus.Success -> {
                                UpdateRatesResult.Success
                            }
                            is UpdateStatus.NotNeededToUpdate -> {
                                UpdateRatesResult.NoNeedUpdate
                            }
                        }
                    }
                    .cast(UpdateRatesResult::class.java)
                    .onErrorReturn(UpdateRatesResult::Failure)
                    .startWith(UpdateRatesResult.Processing)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
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
        }
}
