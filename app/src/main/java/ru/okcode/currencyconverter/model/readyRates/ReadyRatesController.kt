package ru.okcode.currencyconverter.model.readyRates

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import ru.okcode.currencyconverter.model.Rates
import ru.okcode.currencyconverter.model.db.ready.ReadyDao
import javax.inject.Inject

class ReadyRatesController(private val readyDao: ReadyDao) : ReadyRates {

    override fun writeRates(rates: Rates) {
        readyDao.insertToReadyRates()
    }
}