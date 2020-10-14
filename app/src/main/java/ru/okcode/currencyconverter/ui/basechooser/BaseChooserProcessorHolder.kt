package ru.okcode.currencyconverter.ui.basechooser

import android.icu.util.Currency
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.okcode.currencyconverter.data.repository.ConfigRepository
import ru.okcode.currencyconverter.ui.basechooser.BaseChooserAction.*
import ru.okcode.currencyconverter.ui.basechooser.BaseChooserResult.*
import ru.okcode.currencyconverter.util.getFlagRes
import javax.inject.Inject

class BaseChooserProcessorHolder @Inject constructor(
    private val textProcessor: TextProcessor,
    private val configRepository: ConfigRepository
) {
    internal val actionProcessor:
            ObservableTransformer<BaseChooserAction, BaseChooserResult> =
        ObservableTransformer { actions ->
            actions.publish { shared ->
                Observable.merge(
                    shared.ofType(PressDigitAction::class.java)
                        .compose(pressDigitProcessor),
                    shared.ofType(PressAdditionalAction::class.java)
                        .compose(pressAdditionalProcessor),
                    shared.ofType((PressCalculationAction::class.java))
                        .compose(closingByOkProcessor),
                    shared.ofType(LoadCurrencyInfoAction::class.java)
                        .compose(loadCurrencyInfoInfoProcessor),
                )
                    .mergeWith(
                        shared.ofType(CancelAction::class.java)
                            .compose(closingByCancelProcessor)
                    )
                    .mergeWith(
                        shared.filter { action ->
                            action !is PressDigitAction
                                    && action !is PressAdditionalAction
                                    && action !is PressCalculationAction
                                    && action !is LoadCurrencyInfoAction
                                    && action !is CancelAction
                        }
                            .flatMap { action ->
                                Observable.error(
                                    IllegalArgumentException("Unknown Action type: $action")
                                )
                            }
                    )
            }
        }

    private val pressDigitProcessor:
            ObservableTransformer<PressDigitAction, PressDigitResult> =
        ObservableTransformer { actions ->
            actions.map { action ->
                PressDigitResult.Success(
                    textProcessor.pressDigit(action.digitOperand.value)
                )
            }
                .cast(PressDigitResult::class.java)
                .onErrorReturn(PressDigitResult::Failure)
        }

    private val pressAdditionalProcessor:
            ObservableTransformer<PressAdditionalAction, PressAdditionalResult> =
        ObservableTransformer { actions ->
            actions.flatMap { action ->
                when (action.additionalOperand) {
                    is AdditionalOperand.Comma -> {
                        Observable.just(PressAdditionalResult.Success(textProcessor.pressComma()))
                    }
                    is AdditionalOperand.Erase -> {
                        Observable.just(PressAdditionalResult.Success(textProcessor.pressErase()))
                    }

                }
            }
                .cast(PressAdditionalResult::class.java)
                .onErrorReturn(PressAdditionalResult::Failure)
        }

    private val closingByOkProcessor:
            ObservableTransformer<PressCalculationAction, ClosingByOkResult> =
        ObservableTransformer { actions ->
            actions.flatMap { action ->
                configRepository.getConfigSingle()
                    .toObservable()
                    .flatMap { previousConfig ->
                        val newCurrencyCode = action.currencyCode

                        val newCurrencyAmount: Float =
                            when (action.calculation) {
                                Calculation.RESULT_1 -> 1f
                                Calculation.RESULT_10 -> 10f
                                Calculation.RESULT_100 -> 100f
                                Calculation.RESULT_1000 -> 1000f
                                Calculation.RESULT_OVERALL -> textProcessor.getNumberValue()
                            }

                        if (newCurrencyAmount != previousConfig.baseCurrencyAmount ||
                            newCurrencyCode != previousConfig.baseCurrencyCode
                        ) {
                            val newConfig = previousConfig.copy(
                                baseCurrencyCode = newCurrencyCode,
                                baseCurrencyAmount = newCurrencyAmount
                            )
                            configRepository.saveConfig(newConfig)
                        }

                        Observable.just(ClosingByOkResult.Success)
                    }
            }
                .cast(ClosingByOkResult::class.java)
                .onErrorReturn(ClosingByOkResult::Failure)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    private val loadCurrencyInfoInfoProcessor:
            ObservableTransformer<LoadCurrencyInfoAction, LoadCurrencyInfoResult> =
        ObservableTransformer { actions ->
            actions.flatMap { action ->
                Observable.just(
                    LoadCurrencyInfoResult.Success(
                        currency = Currency.getInstance(action.currencyCode),
                        flagRes = getFlagRes(action.currencyCode),
                        displayValue = textProcessor.setDisplayValue(action.currencyAmount)
                    )
                )
            }
                .cast(LoadCurrencyInfoResult::class.java)
                .onErrorReturn(LoadCurrencyInfoResult::Failure)
        }


    private val closingByCancelProcessor:
            ObservableTransformer<CancelAction, ClosingByCancel> =
        ObservableTransformer { actions ->
            actions.map {
                ClosingByCancel.Success
            }
                .cast(ClosingByCancel::class.java)
                .onErrorReturn(ClosingByCancel::Failure)
        }
}
