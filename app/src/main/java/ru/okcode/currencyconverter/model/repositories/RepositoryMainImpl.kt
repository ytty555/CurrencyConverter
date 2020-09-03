package ru.okcode.currencyconverter.model.repositories

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred
import ru.okcode.currencyconverter.model.Config
import ru.okcode.currencyconverter.model.Rates
import javax.inject.Inject

class RepositoryMainImpl @Inject constructor(
    private val cacheRepository: CacheRepository,
    private val configRepository: ConfigRepository,
    private val readyRepository: ReadyRepository
) : RepositoryMain {

    override val configDataSource: LiveData<Config>
        get() = configRepository.configDataSource

    override val cacheDataSource: LiveData<Rates>
        get() = cacheRepository.cacheDataSource

    override val readyRatesDataSource: LiveData<Rates>
        get() = readyRepository.readyRatesDataSource

    override suspend fun refreshData(immediately: Boolean) {
        cacheRepository.refreshCacheRates(immediately)
    }

    override fun getCachedRatesAsync(): Deferred<Rates> {
        TODO("Not yet implemented")
    }

    override fun updateReadyRates(rates: Rates, config: Config) {
        readyRepository.updateReadyRates(rates, config)
    }
}