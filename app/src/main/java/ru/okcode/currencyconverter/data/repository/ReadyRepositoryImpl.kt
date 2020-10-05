package ru.okcode.currencyconverter.data.repository

import android.util.Log
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.Observables
import ru.okcode.currencyconverter.data.db.ready.ReadyRates
import ru.okcode.currencyconverter.data.db.ready.decorator.BaseChangeDecorator
import ru.okcode.currencyconverter.data.db.ready.decorator.CurrencyPriorityChangeDecorator
import ru.okcode.currencyconverter.data.db.ready.decorator.CurrencyVisibilityChangeDecorator
import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.Rates
import javax.inject.Inject

class ReadyRepositoryImpl @Inject constructor(
    private val readyRates: ReadyRates,
    private val rawRatesRepository: RawRatesRepository,
    private val configRepository: ConfigRepository

) : ReadyRepository {

    private val rawRatesDataSource: Observable<Rates>
        get() {
            return rawRatesRepository.getRatesObservable()
                .toObservable()
                .doOnNext {
                    Log.e(">>> 3 >>>", "$it")
                }
        }

    private val configDataSource: Observable<Config>
        get() {
            return configRepository.getConfig()
                .toObservable()
                .doOnNext{
                    Log.e(">>> 3c >>>", "$it")
                }
        }

    override fun getReadyRates(): Observable<Rates> {
        // TODO FIX IT
        val configuredReadyRates = Observables.combineLatest(
            rawRatesDataSource,
            configDataSource
        ) { r, c -> r }

        return configuredReadyRates

//        return rawRatesDataSource

    }

    private fun conversion() = BiFunction { rawRates: Rates, config: Config ->
        return@BiFunction rawRates
    }

    private fun createReadyRates(rawRates: Rates, config: Config): Rates {
        val readyRatesDecorator =
            CurrencyVisibilityChangeDecorator(
                CurrencyPriorityChangeDecorator(
                    BaseChangeDecorator(
                        readyRates,
                        config
                    ), config
                ), config
            )

        return readyRatesDecorator.createReadyRates(rawRates)
    }
}
