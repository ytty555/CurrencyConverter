package ru.okcode.currencyconverter.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
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

    override fun getRates(): Single<Rates> {
        // TODO FIX IT
        // TODO Need to test
        return readyRates.getReadyRates()
            .doOnError {
                val disposable = cacheRepository.getRates()
                    .subscribeBy(
                        onSuccess = {
                            saveRates(it)
                        },
                        onError = {
                            // Do nothing
                        }
                    )
            }
            .repeat(2)
            .firstOrError()
    }

    override fun saveRates(rates: Rates): Completable {
        val configuredRates: ReadyRatesDecorator =
            CurrencyVisibilityChangeDecorator(
                CurrencyPriorityChangeDecorator(
                    BaseChangeDecorator(readyRates)
                )
            )
        return configuredRates.setReadyRates(rates)
    }

}