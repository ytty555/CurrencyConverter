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
        object Success : PressCalculationResult()
        data class Failure(val error: Throwable) : PressCalculationResult()
    }

    sealed class LoadCurrencyInfoResult : BaseChooserResult() {
        data class Success(
            val currency: Currency,
            val flagRes: Int?,
            val displayValue: String
        ) : LoadCurrencyInfoResult()

        data class Failure(val error: Throwable) : LoadCurrencyInfoResult()
    }

    sealed class CancelResult : BaseChooserResult() {
        object Success : CancelResult()
        data class Failure(val error: Throwable) : CancelResult()
    }
}