package ru.okcode.currencyconverter.model.db.cache

import android.icu.math.BigDecimal
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import ru.okcode.currencyconverter.model.readyRates.Rate
import ru.okcode.currencyconverter.model.readyRates.Rates
import ru.okcode.currencyconverter.util.getFlagRes

private const val CODE_EURO = "EUR"

@Entity
data class CacheRatesHeader(
    @PrimaryKey
    val timeLastUpdateUnix: Long,
    val timeNextUpdateUnix: Long,
    val baseCode: String,
)

@Entity
data class CacheCurrencyRate(
    @PrimaryKey
    var currencyCode: String,
    var rateToBase: BigDecimal,
    var timeLastUpdateUnix: Long,
)

data class CacheHeaderWithRates(
    @Embedded val cacheHeader: CacheRatesHeader,
    @Relation(
        parentColumn = "timeLastUpdateUnix",
        entityColumn = "timeLastUpdateUnix"
    )
    val rates: List<CacheCurrencyRate>
)


fun CacheCurrencyRate.getRateToEuro(baseCurrencyRateToEuro: BigDecimal): BigDecimal {
    return baseCurrencyRateToEuro.multiply(rateToBase)
}

fun CacheHeaderWithRates.getBaseCurrencyRateToEuro(): BigDecimal {
    if (cacheHeader.baseCode == CODE_EURO) {
        return BigDecimal.valueOf(1.0)
    }

    val euroRateToBase =
        rates.filter { CODE_EURO == it.currencyCode }[0].rateToBase

    return BigDecimal.valueOf(1.0).divide(euroRateToBase)
}

fun CacheHeaderWithRates.toDomainModel(): Rates {
    val baseCurrencyRateToEuro = getBaseCurrencyRateToEuro()

    val rates: List<Rate> = this.rates.map {
        Rate(
            currencyCode = it.currencyCode,
            rateToBase = it.rateToBase,
            rateToEur = it.getRateToEuro(baseCurrencyRateToEuro),
            sum = it.rateToBase,
            flagRes = getFlagRes(it.currencyCode)
        )
    }
    return Rates(
        baseCurrencyCode = cacheHeader.baseCode,
        baseCurrencyRateToEuro = baseCurrencyRateToEuro,
        rates = rates,
        timeLastUpdateUnix = cacheHeader.timeLastUpdateUnix,
        timeNextUpdateUnix = cacheHeader.timeNextUpdateUnix
    )
}