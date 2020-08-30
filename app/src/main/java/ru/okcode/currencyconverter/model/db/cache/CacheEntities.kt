package ru.okcode.currencyconverter.model.db.cache

import android.icu.math.BigDecimal
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import ru.okcode.currencyconverter.model.readyRates.Rate
import ru.okcode.currencyconverter.model.readyRates.Rates
import ru.okcode.currencyconverter.util.getFlagRes

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
    var rate: BigDecimal,
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

fun CacheHeaderWithRates.toDomainModel(): Rates {
    val rates: List<Rate> = this.rates.map {
        Rate(
            currencyCode = it.currencyCode,
            rate = it.rate,
            sum = it.rate,
            flagRes = getFlagRes(it.currencyCode)
        )
    }
    return Rates(
        baseCurrencyCode = cacheHeader.baseCode,
        rates = rates,
        timeLastUpdateUnix = cacheHeader.timeLastUpdateUnix,
        timeNextUpdateUnix = cacheHeader.timeNextUpdateUnix
    )
}