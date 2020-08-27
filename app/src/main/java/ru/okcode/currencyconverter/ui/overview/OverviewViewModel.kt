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
import ru.okcode.currencyconverter.model.Rates
import ru.okcode.currencyconverter.model.Repository
import ru.okcode.currencyconverter.util.convertToRates
import java.lang.Exception

class OverviewViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _rates = MutableLiveData<Rates>()
    val rates: LiveData<Rates>
        get() = _rates


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        getRates()
    }

    private fun getRates() {
        scope.launch {
            val ratesDtoDeferred = repository.getRatesAsync()
            try {
                val ratesDto = ratesDtoDeferred.await()
                _rates.value = convertToRates(ratesDto)
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}