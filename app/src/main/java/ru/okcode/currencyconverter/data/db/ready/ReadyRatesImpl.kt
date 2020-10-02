package ru.okcode.currencyconverter.data.db.ready

import io.reactivex.Completable
import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Rates
import javax.inject.Inject

class ReadyRatesImpl @Inject constructor(
    private val readyDao: ReadyDao,
    private val readyMapper: ReadyMapper
) : ReadyRates {

    override fun getReadyRates(): Single<Rates> {
        return readyDao.getReadyRatesSingle()
            .map(readyMapper::mapToModel)
    }

    override fun setReadyRates(rates: Rates): Completable {
        readyDao.insert(readyMapper.mapToEntity(rates))
        return Completable.complete()
    }

}