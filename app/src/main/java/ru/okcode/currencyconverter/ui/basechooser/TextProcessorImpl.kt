package ru.okcode.currencyconverter.ui.basechooser

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Текстовый процессор предназначен для генерации числа
 * путем обработки вводимых значений: цифровых символов, знаков и сервисных функций
 */
@Singleton
class TextProcessorImpl @Inject constructor() : TextProcessor {

    /**
     * Хранит текстовое представление генерируемого чилсла
     * для отображения на экране, а так же, как буферное значение
     * в процессе генерации конечного значения
     */
    var displayValue: String = "0"

    companion object {
        const val COMMA = ","
        const val POINT = "."
    }

    /**
     * Функция обрабатывает ввод цифры (0..9)
     * и возвращает генерируемое значение в текстовом виде
     */
    override fun pressDigit(digit: Int): String {
        if (digit < 0 || digit > 9) {
            throw IllegalArgumentException("Argument must be in range 0..9. But received $digit")
        }

        if (displayValue == "0") {
            displayValue = "$digit"
        } else if (displayValue.length < 15) {
            displayValue = "$displayValue$digit"
        }
        return displayValue
    }

    /**
     * Функция обрабатывает ввод запятой
     * и возвращает генерируемое значение в текстовом виде
     */
    override fun pressComma(): String {
        if (!displayValue.contains(COMMA)) {
            displayValue = "$displayValue$COMMA"
        }

        return displayValue
    }

    /**
     * Функция удаляет последний введеный символ
     * и возвращает генерируемое значение в текстовом виде
     */
    override fun pressErase(): String {
        displayValue =
            if (displayValue.length == 1) {
                "0"
            } else {
                displayValue.substring(0, displayValue.lastIndex)
            }
        return displayValue
    }

    /**
     * Функция, возвращает генерируемое значение в числовом виде
     */
    override fun getNumberValue(): Float {
        if (displayValue.contains(COMMA)) {
            displayValue = displayValue.replace(COMMA, POINT)
        }

        val resultValue = displayValue.toFloat()

        return if (resultValue == 0f) {
            1f
        } else {
            resultValue
        }
    }

    /**
     * Функция, устанавливает числовое значение генерируемого числа и
     * возвращает генерируемое значение в текстовом виде
     */
    override fun setDisplayValue(number: Float?): String {
        if (number == null) {
            return displayValue
        }

        if (number == 0f) {
            displayValue = "0"
            return displayValue
        }

        var numberAsText = number.toString()
        numberAsText = numberAsText.replace(POINT, COMMA)

        if (numberAsText.endsWith(",0")) {
            numberAsText = numberAsText.substring(0, numberAsText.lastIndex - 1)
        }

        displayValue = numberAsText
        return displayValue
    }
}