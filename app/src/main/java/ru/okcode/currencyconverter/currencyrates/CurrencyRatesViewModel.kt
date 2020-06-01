package ru.okcode.currencyconverter.currencyrates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.model.DefaultRatesRepository
import ru.okcode.currencyconverter.model.api.RatesData
import ru.okcode.currencyconverter.model.RatesRepository
import ru.okcode.currencyconverter.model.Result

class CurrencyRatesViewModel : ViewModel() {

    private val repository: RatesRepository = DefaultRatesRepository()
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _ratesData = MutableLiveData<RatesData?>()
    val ratesData: LiveData<RatesData?>
        get() = _ratesData

    init {
        getActualRates()
    }

    private fun getActualRates() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
            val resultRatesData: RatesData? = repository.getRates(forceUpdate = true)
                resultRatesData?.let {
                    _ratesData.postValue(it)
                }
            }

        }

    }

}