package ru.okcode.currencyconverter.data.db.cache

import ru.okcode.currencyconverter.data.model.ModelMapper
import ru.okcode.currencyconverter.data.model.Rate
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.util.getBaseCurrencyRateToEuro
import ru.okcode.currencyconverter.util.getFlagRes
import ru.okcode.currencyconverter.util.getRateToEuro
import javax.inject.Inject

class CacheMapper @Inject constructor() : ModelMapper<CacheHeaderWithRates, Rates> {

    override fun mapToModel(entity: CacheHeaderWithRates?): Rates? {
        if (entity == null) {
            return null
        }

        val baseCurrencyRateToEuro = getBaseCurrencyRateToEuro(entity)

        val rates: List<Rate> = entity.rates.map {
            Rate(
                currencyCode = it.currencyCode,
                rateToBase = it.rateToBase,
                rateToEur = getRateToEuro(it, baseCurrencyRateToEuro),
                sum = it.rateToBase,
                flagRes = getFlagRes(it.currencyCode)
            )
        }
        return Rates(
            baseCurrencyCode = entity.cacheHeader.baseCode,
            baseCurrencyRateToEuro = baseCurrencyRateToEuro,
            rates = rates,
            timeLastUpdateUnix = entity.cacheHeader.timeLastUpdateUnix,
            timeNextUpdateUnix = entity.cacheHeader.timeNextUpdateUnix
        )
    }

    override fun mapToEntity(model: Rates): CacheHeaderWithRates {
        val rates: List<CacheCurrencyRate> =
            model.rates.map { rate ->
                CacheCurrencyRate(
                    currencyCode = rate.currencyCode,
                    rateToBase = rate.rateToBase,
                    timeLastUpdateUnix = model.timeLastUpdateUnix,
                )
            }

        val header = CacheRatesHeader(
            timeLastUpdateUnix = model.timeLastUpdateUnix,
            timeNextUpdateUnix = model.timeNextUpdateUnix,
            baseCode = model.baseCurrencyCode
        )

        return CacheHeaderWithRates(
            cacheHeader = header,
            rates = rates
        )
    }
}