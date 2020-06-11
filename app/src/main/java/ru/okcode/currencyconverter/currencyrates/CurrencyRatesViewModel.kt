package ru.okcode.currencyconverter.currencyrates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.okcode.currencyconverter.model.DefaultRatesRepository
import ru.okcode.currencyconverter.model.RatesRepository
import ru.okcode.currencyconverter.model.api.RatesData

class CurrencyRatesViewModel : ViewModel() {

    private val repository: RatesRepository = DefaultRatesRepository()

    private val _errorMessage = MutableLiveData<String>()
    val errorManager: LiveData<String>
        get() = _errorMessage

    private val _ratesData = MutableLiveData<RatesData?>()
    val ratesData: LiveData<RatesData?>
        get() = _ratesData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        getActualRates()
    }

    private fun getActualRates() {
        _isLoading.value = true
        repository.getRawRatesData(true)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ ratesData ->
                _ratesData.value = ratesData
                _isLoading.value = false
            }, {
                _errorMessage.value = it.localizedMessage
                _isLoading.value = false
            })
    }

}