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
import ru.okcode.currencyconverter.model.readyRates.Rates

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
    val readyRates: LiveData<Rates> = repositoryMain.readyRates

    init {
        scope.launch {
            repositoryMain.refreshData(true)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}