package ru.okcode.currencyconverter.data.repository

import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.data.db.ready.ReadyRates
import ru.okcode.currencyconverter.data.db.ready.decorator.BaseChangeDecorator
import ru.okcode.currencyconverter.data.db.ready.decorator.CurrencyPriorityChangeDecorator
import ru.okcode.currencyconverter.data.db.ready.decorator.CurrencyVisibilityChangeDecorator
import ru.okcode.currencyconverter.data.db.ready.decorator.ReadyRatesDecorator
import javax.inject.Inject

class ReadyRepositoryImpl @Inject constructor(
    private val readyRates: ReadyRates,
    private val cacheRepository: CacheRepository
) : ReadyRepository {
    override fun getRates(): Single<Rates> {
        TODO("Not yet implemented")
    }

    override fun saveRates(rates: Rates): Single<Rates> {
        TODO("Not yet implemented")
    }


}