package ru.okcode.currencyconverter.ui.basechooser

import android.icu.util.Currency
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.*
import ru.okcode.currencyconverter.model.repositories.ConfigRepository
import ru.okcode.currencyconverter.util.TextProcessor
import ru.okcode.currencyconverter.util.getFlagRes

class BaseChooserViewModel @ViewModelInject constructor(
    private val configRepository: ConfigRepository,
    private val textProcessor: TextProcessor,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    private val _currencyCodeDataSource = MutableLiveData<String>()
    val currencyDataSource: LiveData<Currency> =
        Transformations.map(_currencyCodeDataSource) { currencyCode ->
            Currency.getInstance(currencyCode)
        }

    val currencyAmountDateSource = textProcessor.resultDataSource

    val currencyFlag: LiveData<Int> = Transformations.map(currencyDataSource) {currency ->
        currency.getFlagRes()
    }

    fun setCurrencyCode(currencyCode: String) {
        _currencyCodeDataSource.value = currencyCode
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

    fun updateBase(currencyCode: String, amount: Float) {
        scope.launch {
            withContext(Dispatchers.IO) {
                configRepository.changeBase(currencyCode, amount)
            }
        }
    }

    fun onClickDigit(digit: Int) {

    }

    fun onClickComma() {

    }

    fun onClickErase() {

    }

    fun onClickOk(currencyCode: String, amount: Float? = null) {

    }
}