package ru.okcode.currencyconverter.data.network

import ru.okcode.currencyconverter.data.model.ModelMapper
import ru.okcode.currencyconverter.data.model.Rate
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.util.getFlagRes
import ru.okcode.currencyconverter.util.getRateToEuro
import javax.inject.Inject

class RatesDtoToRatesMapper @Inject constructor() : ModelMapper<RatesDto, Rates> {
    override fun mapToModel(entity: RatesDto?): Rates? {
        if (entity == null) {
            return null
        }

        val rates = entity.conversionRates.map { pair ->
            val currencyCode = pair.key
            val currencyRate = pair.value
            Rate(
                currencyCode = currencyCode,
                rateToBase = currencyRate,
                rateToEur = getRateToEuro(entity, currencyCode),
                sum = currencyRate,
                flagRes = getFlagRes(currencyCode)
            )
        }

        return Rates(
            baseCurrencyCode = entity.baseCode,
            baseCurrencyRateToEuro = 1f,
            timeNextUpdateUnix = entity.timeNextUpdateUnix,
            timeLastUpdateUnix = entity.timeLastUpdateUnix,
            rates = rates
        )
    }

    override fun mapToEntity(model: Rates): RatesDto {
        TODO("Not yet implemented")
    }

}