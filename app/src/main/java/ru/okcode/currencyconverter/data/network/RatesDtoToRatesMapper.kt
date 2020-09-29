package ru.okcode.currencyconverter.data.network

import android.icu.math.BigDecimal
import android.icu.util.Currency
import ru.okcode.currencyconverter.data.model.ModelMapper
import ru.okcode.currencyconverter.data.model.Rate
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.util.getFlagRes
import javax.inject.Inject

class RatesDtoToRatesMapper @Inject constructor() : ModelMapper<RatesDto, Rates> {
    override fun mapToModel(entity: RatesDto?): Rates? {
        if (entity == null) {
            return null
        }

        val rates = entity.conversionRates.map { pair ->
            Rate(
                currency = Currency.getInstance(pair.key),
                rateToBase = BigDecimal.valueOf(pair.value),
                rateToEur = BigDecimal.valueOf(1.0),
                sum = BigDecimal.valueOf(pair.value),
                flagRes = getFlagRes(pair.key)
            )
        }

        return Rates(
            baseCurrency = Currency.getInstance(entity.baseCode),
            baseCurrencyRateToEuro = BigDecimal.valueOf(1.0),
            timeNextUpdateUnix = entity.timeNextUpdateUnix,
            timeLastUpdateUnix = entity.timeLastUpdateUnix,
            rates = rates
        )
    }

    override fun mapToEntity(model: Rates): RatesDto {
        TODO("Not yet implemented")
    }

}