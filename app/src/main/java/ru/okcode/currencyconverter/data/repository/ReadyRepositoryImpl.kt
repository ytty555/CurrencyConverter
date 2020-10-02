package ru.okcode.currencyconverter.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import ru.okcode.currencyconverter.data.db.ready.ReadyRates
import ru.okcode.currencyconverter.data.db.ready.decorator.BaseChangeDecorator
import ru.okcode.currencyconverter.data.db.ready.decorator.CurrencyPriorityChangeDecorator
import ru.okcode.currencyconverter.data.db.ready.decorator.CurrencyVisibilityChangeDecorator
import ru.okcode.currencyconverter.data.db.ready.decorator.ReadyRatesDecorator
import ru.okcode.currencyconverter.data.model.Rates
import javax.inject.Inject

class ReadyRepositoryImpl @Inject constructor(
    readyRates: ReadyRates,
    private val rawRatesRepository: RawRatesRepository

) : ReadyRepository {

    private val decoratedReadyRates: ReadyRatesDecorator =
        CurrencyVisibilityChangeDecorator(
            CurrencyPriorityChangeDecorator(
                BaseChangeDecorator(readyRates)
            )
        )

    private val disposables = CompositeDisposable()

    override fun subscribeReady() {
        val rawRatesDisposable: Disposable =
            rawRatesRepository.getRatesObservable()
                .subscribeBy(
                    onNext = {
                        saveRates(it)
                    }
                )
        disposables.add(rawRatesDisposable)
    }

    override fun unSubscribeReady() {
        disposables.clear()
    }


    override fun getRates(): Single<Rates> {
        return decoratedReadyRates.getReadyRates()
    }

    override fun saveRates(rates: Rates): Completable {
        return decoratedReadyRates.setReadyRates(rates)
    }


}