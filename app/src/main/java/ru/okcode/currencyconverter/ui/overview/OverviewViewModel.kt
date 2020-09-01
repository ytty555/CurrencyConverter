package ru.okcode.currencyconverter.ui.overview

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.okcode.currencyconverter.model.RepositoryMain
import ru.okcode.currencyconverter.model.readyRates.BaseCurrencyCodeChanger
import ru.okcode.currencyconverter.model.readyRates.Rates
import ru.okcode.currencyconverter.model.readyRates.RatesDecorator
import ru.okcode.currencyconverter.model.readyRates.ReadyRatesController

class OverviewViewModel @ViewModelInject constructor(
    private val repositoryMain: RepositoryMain,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    // Messages
    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    // Ready rates data
    private val _readyRares = MutableLiveData<Rates>()
    val readyRates: LiveData<Rates> = repositoryMain.rawRates

    private val baseCurrencyCode = repositoryMain.baseCurrencyCode


    init {
        scope.launch {
            repositoryMain.refreshData(true)
        }

        repositoryMain.baseCurrencyCode.observeForever {
            updateReadyRates()
        }
    }

    private fun updateReadyRates() {
        val ratesDecorator: RatesDecorator =
            BaseCurrencyCodeChanger(ReadyRatesController(), baseCurrencyCode.value)

        readyRates.value?.let {rates ->
            ratesDecorator.writeRates(rates)
        }

    }

    fun onBaseCurrencyCodeChange(baseCurrencyCode: String) {
        scope.launch {
            repositoryMain.updateBaseCurrencyCode(baseCurrencyCode)
        }
    }

    fun onBaseCurrencyAmountChange(amount: Double) {
        scope.launch {
            repositoryMain.updateBaseCurrencyAmount(amount)
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

}