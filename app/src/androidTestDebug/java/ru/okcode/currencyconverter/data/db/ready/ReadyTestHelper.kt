package ru.okcode.currencyconverter.data.db.ready

class ReadyTestHelper {
    fun getReadyHeaderWithRates01(): ReadyHeaderWithRates {
        val timeLastUpdateUnix = System.currentTimeMillis() / 1000L

        val readyRateUSD = ReadyRate(
            currencyCode = "USD",
            rateToBase = 1.3f,
            rateToEur = 1.3f,
            sum = 1.3f,
            priorityPosition = 0,
            flagRes = null,
            isVisible = true,
            timeLastUpdateUnix = timeLastUpdateUnix
        )

        val readyRateRUB = ReadyRate(
            currencyCode = "RUB",
            rateToBase = 85.6f,
            rateToEur = 85.6f,
            sum = 85.6f,
            priorityPosition = 0,
            flagRes = null,
            isVisible = true,
            timeLastUpdateUnix = timeLastUpdateUnix
        )

        val rates: List<ReadyRate> = listOf(readyRateRUB, readyRateUSD)

        val readyHeader = ReadyHeader(
            baseCurrencyCode = "EUR",
            baseCurrencyAmount = 1f,
            baseCurrencyRateToEuro = 1f,
            timeLastUpdateUnix = timeLastUpdateUnix,
            timeNextUpdateUnix = timeLastUpdateUnix + 60_000
        )

        return ReadyHeaderWithRates(
            readyHeader = readyHeader,
            rates = rates
        )
    }

    fun getReadyHeaderWithRates02(): ReadyHeaderWithRates {
        val timeLastUpdateUnix = System.currentTimeMillis() / 1000L - 300_000

        val readyRateUSD = ReadyRate(
            currencyCode = "USD",
            rateToBase = 1.222f,
            rateToEur = 1.222f,
            sum = 1.222f,
            priorityPosition = 0,
            flagRes = null,
            isVisible = true,
            timeLastUpdateUnix = timeLastUpdateUnix
        )

        val readyRateRUB = ReadyRate(
            currencyCode = "RUB",
            rateToBase = 88.555f,
            rateToEur = 88.555f,
            sum = 88.555f,
            priorityPosition = 0,
            flagRes = null,
            isVisible = true,
            timeLastUpdateUnix = timeLastUpdateUnix
        )

        val rates: List<ReadyRate> = listOf(readyRateRUB, readyRateUSD)

        val readyHeader = ReadyHeader(
            baseCurrencyCode = "EUR",
            baseCurrencyAmount = 1f,
            baseCurrencyRateToEuro = 1f,
            timeLastUpdateUnix = timeLastUpdateUnix,
            timeNextUpdateUnix = timeLastUpdateUnix + 60_000
        )

        return ReadyHeaderWithRates(
            readyHeader = readyHeader,
            rates = rates
        )
    }
}