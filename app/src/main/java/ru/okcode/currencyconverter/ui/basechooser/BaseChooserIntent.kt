package ru.okcode.currencyconverter.ui.basechooser

import ru.okcode.currencyconverter.mvibase.MviIntent

sealed class BaseChooserIntent : MviIntent {
    data class PressDigitIntent(val digitOperand: DigitOperand): BaseChooserIntent()
    data class PressAdditionalIntent(val additionalOperand: AdditionalOperand): BaseChooserIntent()
    data class PressCalculationResultIntent(val calculationResult: Calculation): BaseChooserIntent()
    data class LoadCurrencyInfoIntent(val currencyCode: String, val currencyAmount: Float = 0f): BaseChooserIntent()
    object CancelIntent: BaseChooserIntent()
}

enum class DigitOperand(val value: Int) {
    OPERAND_0(0),
    OPERAND_1(1),
    OPERAND_2(2),
    OPERAND_3(3),
    OPERAND_4(4),
    OPERAND_5(5),
    OPERAND_6(6),
    OPERAND_7(7),
    OPERAND_8(8),
    OPERAND_9(9),
}

enum class AdditionalOperand {
    OPERAND_COMMA,
    OPERAND_CLEAR
}

enum class Calculation {
    RESULT_1,
    RESULT_10,
    RESULT_100,
    RESULT_1000,
    RESULT_OVERALL,
}