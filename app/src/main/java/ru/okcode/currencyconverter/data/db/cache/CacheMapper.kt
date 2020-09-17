package ru.okcode.currencyconverter.data.db.cache

import android.icu.math.BigDecimal
import android.icu.util.Currency
import ru.okcode.currencyconverter.data.model.ModelMapper
import ru.okcode.currencyconverter.data.model.Rate
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.util.getFlagRes
import javax.inject.Inject

private const val CODE_EURO = "EUR"

class CacheMapper @Inject constructor() : ModelMapper<CacheHeaderWithRates, Rates> {

    override fun mapToModel(entity: CacheHeaderWithRates?): Rates? {
        if (entity == null) {
            return null
        }

        val baseCurrencyRateToEuro = getBaseCurrencyRateToEuro(entity)

        val rates: List<Rate> = entity.rates.map {
            Rate(
                currency = Currency.getInstance(it.currencyCode),
                rateToBase = it.rateToBase,
                rateToEur = getRateToEuro(it, baseCurrencyRateToEuro),
                sum = it.rateToBase,
                flagRes = getFlagRes(it.currencyCode)
            )
        }
        return Rates(
            baseCurrency = Currency.getInstance(entity.cacheHeader.baseCode),
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
                    currencyCode = rate.currency.currencyCode,
                    rateToBase = rate.rateToBase,
                    timeLastUpdateUnix = model.timeLastUpdateUnix,
                )
            }

        val header = CacheRatesHeader(
            timeLastUpdateUnix = model.timeLastUpdateUnix,
            timeNextUpdateUnix = model.timeNextUpdateUnix,
            baseCode = model.baseCurrency.currencyCode
        )

        return CacheHeaderWithRates(
            cacheHeader = header,
            rates = rates
        )
    }

    private fun getBaseCurrencyRateToEuro(entity: CacheHeaderWithRates): BigDecimal {
        if (entity.cacheHeader.baseCode == CODE_EURO) {
            return BigDecimal.valueOf(1.0)
        }

        val euroRateToBase =
            entity.rates.filter { CODE_EURO == it.currencyCode }[0].rateToBase

        return BigDecimal.valueOf(1.0).divide(euroRateToBase)
    }

    private fun getRateToEuro(
        cacheCurrencyRate: CacheCurrencyRate,
        baseCurrencyRateToEuro: BigDecimal
    ): BigDecimal {
        return baseCurrencyRateToEuro.multiply(cacheCurrencyRate.rateToBase)
    }
}