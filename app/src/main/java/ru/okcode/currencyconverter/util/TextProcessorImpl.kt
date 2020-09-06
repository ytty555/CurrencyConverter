package ru.okcode.currencyconverter.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

private const val COMMA = ","

class TextProcessorImpl @Inject constructor(): TextProcessor {

    private val _resultDataSource = MutableLiveData<String>()
    override val resultDataSource: LiveData<String>
        get() = _resultDataSource

    init {
        setResult("1")
    }

    override fun pressDigit(digit: Int) {
        val currentValue = getResult()
        setResult("$currentValue$digit")
    }

    override fun pressComma() {
        val currentValue = getResult()
        if (currentValue.hasNOTComma()) {
            setResult("$currentValue$COMMA")
        }
    }

    override fun pressErase() {
        val currentValue = getResult()
        if (currentValue.length == 1) {
            setResult("0")
        } else {
            setResult(currentValue.substring(0, currentValue.lastIndex))
        }
    }

    override fun getResultAsNumber(): Float {
        val currentValue = getResult()

        val resultValue: Float

        resultValue = if (currentValue.endsWith(COMMA)) {
            currentValue.substring(0, currentValue.lastIndex).toFloat()
        } else {
            currentValue.toFloat()
        }

        return if (resultValue == 0f) {
            1f
        } else {
            resultValue
        }
    }

    private fun setResult(str: String) {
        _resultDataSource.value = str
    }

    private fun getResult(): String {
        return resultDataSource.value!!
    }
}

fun String.hasNOTComma(): Boolean {
    return !this.contains(COMMA)
}