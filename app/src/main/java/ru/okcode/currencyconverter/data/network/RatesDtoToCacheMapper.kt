package ru.okcode.currencyconverter.data.network

import android.icu.math.BigDecimal
import ru.okcode.currencyconverter.data.db.cache.CacheCurrencyRate
import ru.okcode.currencyconverter.data.db.cache.CacheHeaderWithRates
import ru.okcode.currencyconverter.data.db.cache.CacheRatesHeader
import ru.okcode.currencyconverter.data.model.ModelMapper
import javax.inject.Inject

class RatesDtoToCacheMapper @Inject constructor(): ModelMapper<RatesDto, CacheHeaderWithRates> {
    override fun mapToModel(entity: RatesDto?): CacheHeaderWithRates? {
        if (entity == null) {
            return null
        }

        val cacheRatesHeader = CacheRatesHeader(
            timeLastUpdateUnix = entity.timeLastUpdateUnix,
            timeNextUpdateUnix = entity.timeNextUpdateUnix,
            baseCode = entity.baseCode
        )

        val rates = entity.conversionRates.map {
            CacheCurrencyRate(
                currencyCode = it.key,
                rateToBase = BigDecimal.valueOf(it.value),
                timeLastUpdateUnix = entity.timeLastUpdateUnix
            )
        }

        return CacheHeaderWithRates(
            cacheRatesHeader,
            rates
        )
    }

    override fun mapToEntity(model: CacheHeaderWithRates): RatesDto {
        TODO("Not yet implemented")
    }

}