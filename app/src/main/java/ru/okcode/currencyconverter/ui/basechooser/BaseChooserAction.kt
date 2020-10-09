package ru.okcode.currencyconverter.ui.basechooser

import ru.okcode.currencyconverter.mvibase.MviAction

sealed class BaseChooserAction : MviAction {
    data class PressDigitAction(val digitOperand: DigitOperand): BaseChooserAction()
    data class PressAdditionalAction(val additionalOperand: AdditionalOperand): BaseChooserAction()
    data class PressCalculationAction(val currencyCode: String, val calculation: Calculation): BaseChooserAction()
    data class LoadCurrencyInfoAction(val currencyCode: String, val currencyAmount: Float?): BaseChooserAction()
    object CancelAction: BaseChooserAction()
}