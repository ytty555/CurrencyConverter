package ru.okcode.currencyconverter.data.repository

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.data.model.ready.ReadyRates
import ru.okcode.currencyconverter.data.model.ready.decorator.BaseChangeDecorator
import ru.okcode.currencyconverter.data.model.ready.decorator.CurrencyPriorityAndVisibilityChangeDecorator
import timber.log.Timber
import javax.inject.Inject

class ReadyRepositoryImpl @Inject constructor(
    private val readyRates: ReadyRates,
    rawRatesRepository: RawRatesRepository,
    configRepository: ConfigRepository

) : ReadyRepository {

    private val rawRatesDataSource: Observable<Rates> =
        rawRatesRepository.getRatesObservable()
            .toObservable()
            .doOnNext {
                Timber.d("Cache changed")
            }


    private val configDataSource: Observable<Config> =
        configRepository.getConfigFlowable()
            .toObservable()
            .distinctUntilChanged()
            .doOnNext {
                Timber.d("Config changed $it")
            }


    override fun getReadyRates(): Observable<Rates> {
        return Observable.combineLatest(
            rawRatesDataSource,
            configDataSource,
            conversion()
        )
    }

    private fun conversion() = BiFunction { rawRates: Rates, config: Config ->
        val readyRatesDecorator =
            CurrencyPriorityAndVisibilityChangeDecorator(
                BaseChangeDecorator(
                    readyRates,
                    config
                ), config
            )

        readyRatesDecorator.createReadyRates(rawRates)
    }

}
