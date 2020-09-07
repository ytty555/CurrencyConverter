package ru.okcode.currencyconverter.model.processor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

/**
 * Текстовый процессор предназначен для генерации числа
 * путем обработки вводимых значений: цифровых символов, знаков и сервисных функций
 */
@FragmentScoped
class TextProcessorImpl @Inject constructor(): TextProcessor {

    /**
     * Хранит текстовое представление генерируемого чилсла
     * для отображения на экране, а так же, как буферное значение
     * в процессе генерации конечного значения
     */
    private val _displayValueDataSource = MutableLiveData<String>()
    override val displayValueDataSource: LiveData<String>
        get() = _displayValueDataSource

    init {
        setDisplayValue("1")
    }

    companion object {
        const val COMMA = ","
    }

    /**
     * Функция обработки ввода цифр (0..9)
     */
    override fun pressDigit(digit: Int) {
        TODO("Not yet implemented")
    }

    /**
     * Функция обработки ввода запятой
     */
    override fun pressComma() {
        TODO("Not yet implemented")
    }

    /**
     * Функция удаления последнего введеного символа
     */
    override fun pressErase() {
        TODO("Not yet implemented")
    }

    /**
     * Функция, возвращающая генерируемое значение числовом виде
     */
    override fun getNumberValue(): Float {
        val displayValue = getDisplayValue()
        return convertToFloat(displayValue)
    }

    private fun convertToFloat(value: String): Float {
        TODO("Not yet implemented")
    }

    private fun setDisplayValue(value: String) {
        _displayValueDataSource.value = value
    }

    private fun getDisplayValue(): String {
        return displayValueDataSource.value!!
    }


}