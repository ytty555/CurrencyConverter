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

    sealed class LoadCurrencyInfoResult : BaseChooserResult() {
        data class Success(
            val currency: Currency,
            val flagRes: Int?,
            val displayValue: String
        ) : LoadCurrencyInfoResult()

        data class Failure(val error: Throwable) : LoadCurrencyInfoResult()
    }

    sealed class ClosingByOkResult : BaseChooserResult() {
        data class Success(val amountAsText: String) : ClosingByOkResult()
        data class Failure(val error: Throwable) : ClosingByOkResult()
    }

    sealed class ClosingByCancel : BaseChooserResult() {
        object Success : ClosingByCancel()
        data class Failure(val error: Throwable) : ClosingByCancel()
    }
}