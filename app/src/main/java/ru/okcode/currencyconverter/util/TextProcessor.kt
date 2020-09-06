package ru.okcode.currencyconverter.util

import androidx.lifecycle.LiveData

interface TextProcessor {
    val resultDataSource: LiveData<String>

    fun pressDigit(digit: Int)

    fun pressComma()

    fun pressErase()

    fun getResultAsNumber(): Float

}