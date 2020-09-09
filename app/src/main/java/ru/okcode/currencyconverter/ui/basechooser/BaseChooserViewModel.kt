package ru.okcode.currencyconverter.ui.basechooser

import android.icu.util.Currency
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.*
import ru.okcode.currencyconverter.model.processor.TextProcessor
import ru.okcode.currencyconverter.model.repositories.ConfigRepository
import ru.okcode.currencyconverter.util.getFlagRes

class BaseChooserViewModel @ViewModelInject constructor(
    private val configRepository: ConfigRepository,
    private val textProcessor: TextProcessor
) : ViewModel() {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    private val _currencyCodeDataSource = MutableLiveData<String>()
    val currencyDataSource: LiveData<Currency> =
        Transformations.map(_currencyCodeDataSource) { currencyCode ->
            Currency.getInstance(currencyCode)
        }

    val currencyAmountDateSource: LiveData<String> = textProcessor.displayValueDataSource

    val currencyFlag: LiveData<Int> = Transformations.map(currencyDataSource) { currency ->
        currency.getFlagRes()
    }

    private val _closeBaseChooser = MutableLiveData<Boolean>()
    val closeBaseChooser: LiveData<Boolean>
        get() = _closeBaseChooser

    init {
        _closeBaseChooser.value = false
        textProcessor.setDisplayValue("0")
    }

    fun setCurrencyCode(currencyCode: String) {
        _currencyCodeDataSource.value = currencyCode
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

    private fun updateBase(currencyCode: String) {
        val resultValue: Float = textProcessor.getNumberValue()

        scope.launch {
            withContext(Dispatchers.IO) {
                configRepository.changeBase(currencyCode, resultValue)
            }
        }
    }

    fun onClickDigit(digit: Int) {
        textProcessor.pressDigit(digit)
    }

    fun onClickComma() {
        textProcessor.pressComma()
    }

    fun onClickErase() {
        textProcessor.pressErase()
    }

    fun onClickOkFixedValue(currencyCode: String, amount: Float) {
        textProcessor.setDisplayValue(amount.toString())
        updateBase(currencyCode)
        _closeBaseChooser.value = true
    }

    fun onClickOk(currencyCode: String) {
        updateBase(currencyCode)
        _closeBaseChooser.value = true
    }
}