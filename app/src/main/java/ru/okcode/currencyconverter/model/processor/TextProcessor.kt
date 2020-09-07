package ru.okcode.currencyconverter.model.processor

import androidx.lifecycle.LiveData

interface TextProcessor {
    val displayValueDataSource: LiveData<String>

    fun pressDigit(digit: Int)

    fun pressComma()

    fun pressErase()

    fun getNumberValue(): Float

}