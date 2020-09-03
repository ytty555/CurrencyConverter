package ru.okcode.currencyconverter.model.readyRates

import android.util.Log
import ru.okcode.currencyconverter.model.Rates
import ru.okcode.currencyconverter.model.db.ready.ReadyDao
import ru.okcode.currencyconverter.model.db.ready.ReadyMapper

private const val TAG = "ReadyRatesController"

class ReadyRatesController(
    private val readyDao: ReadyDao,
    private val readyMapper: ReadyMapper
) : ReadyRates {

    override suspend fun writeRates(rates: Rates) {
        readyDao.insertToReadyRates(readyMapper.mapToEntity(rates))
    }
}