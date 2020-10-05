package ru.okcode.currencyconverter.ui.basechooser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

/**
 * Текстовый процессор предназначен для генерации числа
 * путем обработки вводимых значений: цифровых символов, знаков и сервисных функций
 */
@ActivityRetainedScoped
class TextProcessorImpl @Inject constructor() : TextProcessor {

    /**
     * Хранит текстовое представление генерируемого чилсла
     * для отображения на экране, а так же, как буферное значение
     * в процессе генерации конечного значения
     */
    private val _displayValueDataSource = MutableLiveData<String>()
    override val displayValueDataSource: LiveData<String>
        get() = _displayValueDataSource

    companion object {
        const val COMMA = ","
        const val POINT = "."
    }

    /**
     * Функция обработки ввода цифр (0..9)
     */
    override fun pressDigit(digit: Int) {
        if (digit < 0 || digit > 9) {
            return
        }

        val displayValue = getDisplayValue()

        if (displayValue != "0") {
            setDisplayValue("$displayValue$digit")
        } else {
            setDisplayValue("$digit")
        }
    }

    /**
     * Функция обработки ввода запятой
     */
    override fun pressComma() {
        val displayValue = getDisplayValue()
        if (!displayValue.contains(COMMA)) {
            setDisplayValue("$displayValue$COMMA")
        }
    }

    /**
     * Функция удаления последнего введеного символа
     */
    override fun pressErase() {
        val displayValue = getDisplayValue()

        if (displayValue.length == 1) {
            setDisplayValue("0")
        } else {
            setDisplayValue(displayValue.substring(0, displayValue.lastIndex))
        }

    }

    /**
     * Функция, возвращающая генерируемое значение числовом виде
     */
    override fun getNumberValue(): Float {
        var displayValue = getDisplayValue()

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
     * Функция полностью перезаписывает значение строкового
     * представления генерируемого числа
     */
    override fun setDisplayValue(value: String) {
        _displayValueDataSource.value = value
    }

    private fun getDisplayValue(): String {
        return displayValueDataSource.value!!
    }


}