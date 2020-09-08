package ru.okcode.currencyconverter.model.processor

import androidx.lifecycle.LiveData

/**
 * Интерфейс текстового процессора, предназначенного для генерации числа
 * путем обработки вводимых значений: цифровых символов, знаков и сервисных функций
 */
interface TextProcessor {

    /**
     * Хранит текстовое представление генерируемого чилсла
     * для отображения на экране, а так же, как буферное значение
     * в процессе генерации конечного значения
     */
    val displayValueDataSource: LiveData<String>

    /**
     * Функция обработки ввода цифр (0..9)
     */
    fun pressDigit(digit: Int)

    /**
     * Функция обработки ввода запятой
     */
    fun pressComma()

    /**
     * Функция удаления последнего введеного символа
     */
    fun pressErase()

    /**
     * Функция, возвращающая генерируемое значение числовом виде
     */
    fun getNumberValue(): Float

    /**
     * Функция полностью перезаписывает значение строкового
     * представления генерируемого числа
     */
    fun setDisplayValue(value: String)

}