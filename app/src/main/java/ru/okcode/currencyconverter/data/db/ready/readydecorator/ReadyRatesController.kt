package ru.okcode.currencyconverter.data.db.ready.readydecorator

import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.data.db.ready.ReadyDao
import ru.okcode.currencyconverter.data.db.ready.ReadyMapper

class ReadyRatesController(
    private val readyDao: ReadyDao,
    private val readyMapper: ReadyMapper
) : ReadyRates {

    override suspend fun writeRates(rates: Rates) {
        readyDao.insertToReadyRates(readyMapper.mapToEntity(rates))
    }
}