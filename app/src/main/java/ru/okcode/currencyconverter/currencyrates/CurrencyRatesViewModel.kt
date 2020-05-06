package ru.okcode.currencyconverter.currencyrates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.model.RatesData
import ru.okcode.currencyconverter.model.RatesRepository
import ru.okcode.currencyconverter.model.Result
import ru.okcode.currencyconverter.model.db.CurrencyRatesList

class CurrencyRatesViewModel(
    private val repository: RatesRepository
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _snackbarText = MutableLiveData<Int>()
    val snackbarText: LiveData<Int> = _snackbarText


    private val _ratesData = MutableLiveData<RatesData?>()
    val ratesData: LiveData<RatesData?>
        get() = _ratesData

    init {
        getActualRates()
    }

    fun getActualRates() {
        uiScope.launch {
            val resultRatesData: Result<RatesData> = repository.getRates(forceUpdate = true)
            _ratesData.value = computeResult(resultRatesData)

        }
    }

    private fun computeResult(ratesData: Result<RatesData>?): RatesData? {
        return if (ratesData != null && ratesData is Result.Success) {
            ratesData.data
        } else {
            showSnackbarMessage(R.string.err_loading_from_remote_api)
            null
        }
    }

    private fun showSnackbarMessage(messageRes: Int) {
        _snackbarText.value = messageRes
    }

}