package ru.okcode.currencyconverter.ui.overview

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.okcode.currencyconverter.data.repository.ReadyRepository
import ru.okcode.currencyconverter.ui.overview.OverviewAction.*
import ru.okcode.currencyconverter.ui.overview.OverviewResult.*
import javax.inject.Inject

class OverviewProcessorHolder @Inject constructor(
    private val readyRepository: ReadyRepository
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
                ).mergeWith(
                    shared.filter { action ->
                        action !is EditCurrencyListAction
                                && action !is LoadAllRatesAction
                                && action !is ChangeBaseCurrencyAction
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
                readyRepository.getAllRates()
                    .map { rates ->
                        LoadAllRatesResult.Success(rates)
                    }
                    .cast(LoadAllRatesResult::class.java)
                    .onErrorReturn(LoadAllRatesResult::Failure)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(LoadAllRatesResult.Processing)
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
                .observeOn(AndroidSchedulers.mainThread())
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
