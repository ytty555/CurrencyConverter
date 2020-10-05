package ru.okcode.currencyconverter.data.repository

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import ru.okcode.currencyconverter.data.db.ready.ReadyRates
import ru.okcode.currencyconverter.data.db.ready.decorator.BaseChangeDecorator
import ru.okcode.currencyconverter.data.db.ready.decorator.CurrencyPriorityChangeDecorator
import ru.okcode.currencyconverter.data.db.ready.decorator.CurrencyVisibilityChangeDecorator
import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.Rates
import javax.inject.Inject

class ReadyRepositoryImpl @Inject constructor(
    private val readyRates: ReadyRates,
    rawRatesRepository: RawRatesRepository,
    configRepository: ConfigRepository

) : ReadyRepository {

    private val rawRatesDataSource: Observable<Rates> =
        rawRatesRepository.getRatesObservable()
            .toObservable()


    private val configDataSource: Observable<Config> =
        configRepository.getConfig()
            .toObservable()


    override fun getReadyRates(): Observable<Rates> {
        return Observable.combineLatest(
            rawRatesDataSource,
            configDataSource,
            conversion()
        )


    }

    private fun conversion() = BiFunction { rawRates: Rates, config: Config ->
        val readyRatesDecorator =
            CurrencyVisibilityChangeDecorator(
                CurrencyPriorityChangeDecorator(
                    BaseChangeDecorator(
                        readyRates,
                        config
                    ), config
                ), config
            )

        readyRatesDecorator.createReadyRates(rawRates)
    }

}
