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
    val rates: LiveData<Rates> = repository.rates

    init {
        scope.launch {
            repository.refreshCacheRates()
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}