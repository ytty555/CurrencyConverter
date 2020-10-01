package ru.okcode.currencyconverter.data.repository

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.Predicate
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.delay
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.data.ready.ReadyRates
import ru.okcode.currencyconverter.data.ready.decorator.BaseChangeDecorator
import ru.okcode.currencyconverter.data.ready.decorator.CurrencyPriorityChangeDecorator
import ru.okcode.currencyconverter.data.ready.decorator.CurrencyVisibilityChangeDecorator
import ru.okcode.currencyconverter.data.ready.decorator.ReadyRatesDecorator
import javax.inject.Inject

class ReadyRepositoryImpl @Inject constructor(
    private val readyRates: ReadyRates,
    private val cacheRepository: CacheRepository
) : ReadyRepository {

    private val configuredRates: ReadyRatesDecorator =
        CurrencyVisibilityChangeDecorator(
            CurrencyPriorityChangeDecorator(
                BaseChangeDecorator(readyRates)
            )
        )

    override fun getRates(): Single<Rates> {
        // TODO FIX IT
        // TODO Need to test
        return readyRates.getReadyRates()
            .doOnError {
                val disposable = cacheRepository.getRates()
                    .subscribeBy(
                        onSuccess = {
                            Log.e("ee", "--> doOnError rate $it")
                            configuredRates.setReadyRates(it)
                        },
                        onError = {
                            // Do nothing
                        }
                    )
            }
            .retry(1)
    }

    override fun saveRates(rates: Rates): Completable {
        return configuredRates.setReadyRates(rates)
    }

}