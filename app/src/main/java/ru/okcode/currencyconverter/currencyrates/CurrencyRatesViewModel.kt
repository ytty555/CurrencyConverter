package ru.okcode.currencyconverter.currencyrates

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.okcode.currencyconverter.model.CommonRates
import ru.okcode.currencyconverter.model.DefaultRatesRepository
import ru.okcode.currencyconverter.model.RatesRepository

class CurrencyRatesViewModel : ViewModel() {

    private val repository: RatesRepository = DefaultRatesRepository()

    private val _errorMessage = MutableLiveData<String>()
    val errorManager: LiveData<String>
        get() = _errorMessage

    private val _ratesData = MutableLiveData<CommonRates>()
    val ratesData: LiveData<CommonRates>
        get() = _ratesData


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        getActualRates()
    }

    @SuppressLint("CheckResult")
    private fun getActualRates() {
        _isLoading.value = true
        repository.getRatesDataSource(true)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({commonRates ->
                _ratesData.value = commonRates
                _isLoading.value = false
            }, {
                _errorMessage.value = it.localizedMessage
                _isLoading.value = false
            })
    }

}