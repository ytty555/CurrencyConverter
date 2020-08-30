package ru.okcode.currencyconverter.model.readyRates

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.okcode.currencyconverter.model.db.ready.ReadyDao
import ru.okcode.currencyconverter.model.db.ready.ReadyHeader
import ru.okcode.currencyconverter.model.db.ready.ReadyRate
import javax.inject.Inject

class ReadyRatesHandler : ReadyRates {
    @Inject
    lateinit var readyDao: ReadyDao

    private val job = Job()
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + job)

    override fun writeRates(rates: Rates) {
        scope.launch {
            val readyHeader = rates.toReadyHeader()
            val readyRates = rates.toReadyRates(readyHeader.timeLastUpdateUnix)

            readyDao.insertToReadyRates(
                readyHeader,
                readyRates
            )
        }
    }
}

private fun Rates.toReadyHeader(): ReadyHeader {
    return ReadyHeader(
        baseCurrencyCode = this.baseCurrencyCode,
        baseCurrencyAmount = this.baseCurrencyAmount,
        timeLastUpdateUnix = this.timeLastUpdateUnix,
        timeNextUpdateUnix = this.timeNextUpdateUnix

    )
}

private fun Rates.toReadyRates(
    timeLastUpdateUnix: Long,
): List<ReadyRate> {
    return this.rates.map { rate ->
        ReadyRate(
            currencyCode = rate.currencyCode,
            rate = rate.rate,
            sum = rate.sum,
            timeLastUpdateUnix = timeLastUpdateUnix,
            priorityPosition = rate.priorityPosition
        )
    }
}