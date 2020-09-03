package ru.okcode.currencyconverter.model.db.ready

import android.icu.util.Currency
import ru.okcode.currencyconverter.model.ModelMapper
import ru.okcode.currencyconverter.model.Rate
import ru.okcode.currencyconverter.model.Rates
import ru.okcode.currencyconverter.util.getFlagRes
import javax.inject.Inject

class ReadyMapper @Inject constructor() : ModelMapper<ReadyHeaderWithRates, Rates> {

    override fun mapToModel(entity: ReadyHeaderWithRates): Rates {
        val rates: List<Rate> = entity.rates.map { readyRate ->
            Rate(
                currency = Currency.getInstance(readyRate.currencyCode),
                rateToBase = readyRate.rateToBase,
                rateToEur = readyRate.rateToEuro,
                sum = readyRate.sum,
                priorityPosition = readyRate.priorityPosition,
                flagRes = getFlagRes(readyRate.currencyCode)
            )
        }

        return Rates(
            baseCurrency = Currency.getInstance(entity.readyHeader.baseCurrencyCode),
            baseCurrencyAmount = entity.readyHeader.baseCurrencyAmount,
            baseCurrencyRateToEuro = entity.readyHeader.baseCurrencyRateToEuro,
            rates = rates,
            timeLastUpdateUnix = entity.readyHeader.timeLastUpdateUnix,
            timeNextUpdateUnix = entity.readyHeader.timeNextUpdateUnix
        )
    }

    override fun mapToEntity(model: Rates): ReadyHeaderWithRates {
        val readyHeader = ReadyHeader(
            baseCurrencyCode = model.baseCurrency.currencyCode,
            baseCurrencyAmount = model.baseCurrencyAmount,
            baseCurrencyRateToEuro = model.baseCurrencyRateToEuro,
            timeLastUpdateUnix = model.timeLastUpdateUnix,
            timeNextUpdateUnix = model.timeNextUpdateUnix
        )

        val rates: List<ReadyRate> = model.rates.map { rate ->
            ReadyRate(
                currencyCode = rate.currency.currencyCode,
                rateToBase = rate.rateToBase,
                rateToEuro = rate.rateToEur,
                sum = rate.sum,
                timeLastUpdateUnix = readyHeader.timeLastUpdateUnix,
                priorityPosition = rate.priorityPosition
            )
        }

        return ReadyHeaderWithRates(
            readyHeader = readyHeader,
            rates = rates
        )
    }
}