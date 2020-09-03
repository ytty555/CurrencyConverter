package ru.okcode.currencyconverter.model.readyRates

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.okcode.currencyconverter.model.Rates
import ru.okcode.currencyconverter.model.db.ready.ReadyDao
import ru.okcode.currencyconverter.model.db.ready.ReadyHeader
import ru.okcode.currencyconverter.model.db.ready.ReadyRate
import javax.inject.Inject

class ReadyRatesController : ReadyRates {
    @Inject
    lateinit var readyDao: ReadyDao

    private val job = Job()
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + job)

    override fun writeRates(rates: Rates) {
        TODO("Not implemented yet")
    }
}