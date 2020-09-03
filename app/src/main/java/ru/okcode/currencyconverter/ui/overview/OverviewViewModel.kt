package ru.okcode.currencyconverter.ui.overview

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.okcode.currencyconverter.model.Config
import ru.okcode.currencyconverter.model.Rates
import ru.okcode.currencyconverter.model.repositories.RepositoryMain

class OverviewViewModel @ViewModelInject constructor(
    private val repositoryMain: RepositoryMain,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Coroutines scope
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    // Messages ---------------------------------------------------------------------
    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    //Cache data ---------------------------------------------------------------------
    private val cacheDataSource: LiveData<Rates>
        get() = repositoryMain.cacheDataSource

    private val cacheObserver: Observer<Rates> = Observer { cachedRates ->
        val config: Config = configDataSource.value ?: Config.getDefaultConfig()

        repositoryMain.updateReadyRates(cachedRates, config)
    }

    //Config data ---------------------------------------------------------------------
    private val configDataSource: LiveData<Config>
        get() = repositoryMain.configDataSource

    private val configObserver: Observer<Config> = Observer { config ->
        scope.launch {
            val cachedRates: Rates =
                cacheDataSource.value ?: repositoryMain.getCachedRatesAsync().await()
            repositoryMain.updateReadyRates(cachedRates, config)
        }
    }

    // ReadyRates
    val readyRatesDataSource: LiveData<Rates>
        get() = repositoryMain.readyRatesDataSource

    init {
        // TODO ??????????????????????????????????????
        scope.launch {
            repositoryMain.refreshData(true)
        }

        startObserve()
    }

    override fun onCleared() {
        stopObserve()
        job.cancel()
        super.onCleared()
    }

    private fun startObserve() {
        cacheDataSource.observeForever(cacheObserver)
        configDataSource.observeForever(configObserver)
    }

    private fun stopObserve() {
        cacheDataSource.removeObserver(cacheObserver)
        configDataSource.removeObserver(configObserver)
    }


}