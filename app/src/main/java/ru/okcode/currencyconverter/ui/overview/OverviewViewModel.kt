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

class OverviewViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    // Error messages
    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    // Rates data
    private val _rates = MutableLiveData<Rates>()
    val rates: LiveData<Rates>
        get() = _rates

    // Loading status
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    init {
        getRates()
    }

    private fun getRates() {
        scope.launch {
            _status.value = ApiStatus.LOADING
            val ratesDtoDeferred = repository.getRatesAsync()
            try {
                val ratesDto = ratesDtoDeferred.await()
                _rates.value = convertToRates(ratesDto)
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _message.value = e.localizedMessage
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}

enum class ApiStatus {
    LOADING,
    ERROR,
    DONE
}