package ru.okcode.currencyconverter.util

import android.icu.math.BigDecimal
import android.icu.util.Currency
import ru.okcode.currencyconverter.model.CurrencyEnum
import ru.okcode.currencyconverter.model.Rate
import ru.okcode.currencyconverter.model.Rates
import ru.okcode.currencyconverter.model.api.RatesDto
import java.util.*

fun convertToRates(ratesDto: RatesDto): Rates {
    val ratesList: List<Rate> = ratesDto.conversionRates.map {pair ->
        val currencyCode = pair.key

        // get Currency
        val currency = Currency.getInstance(currencyCode)

        // get Rate
        val rate: BigDecimal = BigDecimal.valueOf(pair.value)

        // get Flag
        val flagRes = try {
            CurrencyEnum.valueOf(currencyCode).flagRes
        } catch (e: IllegalArgumentException) {
            null
        }
        Rate(currency = currency, value = rate, flagRes = flagRes)
    }
    return Rates(
        actualDate = Date(),// TODO Fix it!!!
        baseCurrency = Currency.getInstance(ratesDto.baseCode),
        rates = ratesList
    )
}