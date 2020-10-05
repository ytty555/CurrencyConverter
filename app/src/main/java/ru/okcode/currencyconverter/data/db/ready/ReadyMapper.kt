package ru.okcode.currencyconverter.data.db.ready

import ru.okcode.currencyconverter.data.model.ModelMapper
import ru.okcode.currencyconverter.data.model.Rate
import ru.okcode.currencyconverter.data.model.Rates
import javax.inject.Inject

class ReadyMapper @Inject constructor() : ModelMapper<ReadyHeaderWithRates, Rates> {
    override fun mapToModel(entity: ReadyHeaderWithRates?): Rates? {
        if (entity == null) {
            return null
        }

        val rates: List<Rate> = entity.rates.map { readyRate ->
            Rate(
                currencyCode = readyRate.currencyCode,
                rateToBase = readyRate.rateToBase,
                rateToEur = readyRate.rateToEur,
                sum = readyRate.sum,
                priorityPosition = readyRate.priorityPosition,
                flagRes = readyRate.flagRes,
                isVisible = readyRate.isVisible
            )
        }

        return Rates(
            baseCurrencyCode = entity.readyHeader.baseCurrencyCode,
            baseCurrencyAmount = entity.readyHeader.baseCurrencyAmount,
            baseCurrencyRateToEuro = entity.readyHeader.baseCurrencyRateToEuro,
            rates = rates,
            timeLastUpdateUnix = entity.readyHeader.timeLastUpdateUnix,
            timeNextUpdateUnix = entity.readyHeader.timeNextUpdateUnix
        )
    }

    override fun mapToEntity(model: Rates): ReadyHeaderWithRates {
        val readyRates: List<ReadyRate> = model.rates.map { rate ->
            ReadyRate(
                currencyCode = rate.currencyCode,
                rateToBase = rate.rateToBase,
                rateToEur = rate.rateToEur,
                sum = rate.sum,
                priorityPosition = rate.priorityPosition,
                flagRes = rate.flagRes,
                isVisible = rate.isVisible,
                timeLastUpdateUnix = model.timeLastUpdateUnix
            )
        }

        val readyHeader: ReadyHeader = ReadyHeader(
            baseCurrencyCode = model.baseCurrencyCode,
            baseCurrencyAmount = model.baseCurrencyAmount,
            baseCurrencyRateToEuro = model.baseCurrencyRateToEuro,
            timeLastUpdateUnix = model.timeLastUpdateUnix,
            timeNextUpdateUnix = model.timeNextUpdateUnix
        )

        return ReadyHeaderWithRates(
            readyHeader,
            readyRates
        )
    }

}