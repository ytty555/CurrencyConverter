package ru.okcode.currencyconverter.ui.basechooser

import android.icu.util.Currency
import ru.okcode.currencyconverter.mvibase.MviResult

sealed class BaseChooserResult : MviResult {

    sealed class PressDigitResult : BaseChooserResult() {
        data class Success(val displayValue: String) : PressDigitResult()
        data class Failure(val error: Throwable) : PressDigitResult()
    }

    sealed class PressAdditionalResult : BaseChooserResult() {
        data class Success(val displayValue: String) : PressAdditionalResult()
        data class Failure(val error: Throwable) : PressAdditionalResult()
    }

    sealed class PressCalculationResult : BaseChooserResult() {
        data class Success(val displayValue: String, val currencyAmount: Float) :
            PressCalculationResult()

        data class Failure(val error: Throwable) : PressCalculationResult()
    }

    sealed class LoadCurrencyResult : BaseChooserResult() {
        data class Success(val currency: Currency, val startAmount: Float) : LoadCurrencyResult()
        data class Failure(val error: Throwable) : LoadCurrencyResult()
    }

    sealed class CancelResult : BaseChooserResult() {
        object Success : CancelResult()
        data class Failure(val error: Throwable) : CancelResult()
    }
}