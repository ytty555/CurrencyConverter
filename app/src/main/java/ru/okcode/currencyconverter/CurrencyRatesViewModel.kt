package ru.okcode.currencyconverter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.okcode.currencyconverter.model.CurrencyEnum
import ru.okcode.currencyconverter.model.CurrencyRateDto
import ru.okcode.currencyconverter.model.CurrencyRatesRepository

class CurrencyRatesViewModel : ViewModel() {
    private val TAG: String = "CurrencyRatesViewModel"
    private val repository = CurrencyRatesRepository()

    val _rates = MutableLiveData<List<CurrencyRateDto>>(repository.rates)
    val rates: LiveData<List<CurrencyRateDto>>
        get() = _rates

    val _baseCurrency = MutableLiveData<CurrencyEnum>(CurrencyEnum.EUR)
    val baseCurrency: LiveData<CurrencyEnum>
        get() = _baseCurrency



    fun changeBaseCurrency() {

        Log.i(TAG, "changeBaseCurrency()")
        if (_baseCurrency.value == CurrencyEnum.EUR) {
            _baseCurrency.value = CurrencyEnum.RUB
        } else {
            _baseCurrency.value = CurrencyEnum.EUR
        }
    }

    fun changeRates() {
        Log.i(TAG, "changeRates()")
        TODO()
    }
}