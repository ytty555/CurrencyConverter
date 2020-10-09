package ru.okcode.currencyconverter.ui.basechooser

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import ru.okcode.currencyconverter.mvibase.MviViewModel
import ru.okcode.currencyconverter.ui.basechooser.BaseChooserAction.*
import ru.okcode.currencyconverter.ui.basechooser.BaseChooserIntent.*
import ru.okcode.currencyconverter.ui.basechooser.BaseChooserResult.*

class BaseChooserViewModel @ViewModelInject constructor(
    actionProcessorHolder: BaseChooserProcessorHolder
) : ViewModel(), MviViewModel<BaseChooserIntent, BaseChooserViewState> {

    private val intentsPublisher =
        PublishSubject.create<BaseChooserIntent>()

    private val viewStateBehavior =
        BehaviorSubject.create<BaseChooserViewState>()

    override fun processIntents(intents: Observable<BaseChooserIntent>) {
        intents.subscribe(intentsPublisher)
    }

    override fun states(): Observable<BaseChooserViewState> = viewStateBehavior

    init {
        intentsPublisher
            .map(this::actionFromIntent)
            .compose(actionProcessorHolder.actionProcessor)
            .scan(BaseChooserViewState.idle(), reducer)
            .filter {
                it.currency != null
            }
            .distinctUntilChanged()
            .subscribe(viewStateBehavior)
    }

    private fun actionFromIntent(intent: BaseChooserIntent): BaseChooserAction {
        return when (intent) {
            is PressDigitIntent -> PressDigitAction(intent.digitOperand)
            is PressAdditionalIntent -> PressAdditionalAction(intent.additionalOperand)
            is PressCalculationIntent -> PressCalculationAction(
                intent.currencyCode,
                intent.calculation
            )
            is LoadCurrencyInfoIntent -> LoadCurrencyInfoAction(
                intent.currencyCode,
                intent.currencyAmount
            )
            is CancelIntent -> CancelAction
        }
    }

    companion object {
        private val reducer =
            BiFunction { previousState: BaseChooserViewState, result: BaseChooserResult ->
                when (result) {
                    is PressDigitResult -> when (result) {
                        is PressDigitResult.Success -> {
                            previousState.copy(
                                displayValue = result.displayValue,
                                error = null,
                            )
                        }
                        is PressDigitResult.Failure -> {
                            previousState.copy(
                                error = result.error
                            )
                        }
                    }

                    is PressAdditionalResult -> when (result) {
                        is PressAdditionalResult.Success -> {
                            previousState.copy(
                                displayValue = result.displayValue,
                                error = null,
                            )
                        }
                        is PressAdditionalResult.Failure -> {
                            previousState.copy(
                                error = result.error
                            )
                        }
                    }

                    is PressCalculationResult -> when (result) {
                        is PressCalculationResult.Success -> {
                            previousState.copy(
                                error = null
                            )
                        }
                        is PressCalculationResult.Failure -> {
                            previousState.copy(
                                error = result.error
                            )
                        }
                    }

                    is LoadCurrencyInfoResult -> when (result) {
                        is LoadCurrencyInfoResult.Success -> {
                            previousState.copy(
                                currency = result.currency,
                                flagRes = result.flagRes,
                                displayValue = result.displayValue,
                                error = null
                            )
                        }
                        is LoadCurrencyInfoResult.Failure -> {
                            previousState.copy(
                                error = result.error
                            )
                        }
                    }

                    is CancelResult -> when (result) {
                        is CancelResult.Success -> {
                            previousState.copy(
                                error = null
                            )
                        }
                        is CancelResult.Failure -> {
                            previousState.copy(
                                error = result.error
                            )
                        }
                    }
                }

            }
    }

}