package ru.okcode.currencyconverter.model.db

import android.icu.math.BigDecimal
import android.icu.util.Currency
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import ru.okcode.currencyconverter.model.Rate
import ru.okcode.currencyconverter.model.Rates
import ru.okcode.currencyconverter.util.getFlagRes
import java.util.*

@Entity
data class CacheRatesHeader(
    @PrimaryKey
    val timeLastUpdate: String,
    val timeNextUpdate: String,
    val baseCode: String,
)

@Entity
data class CacheCurrencyRate(
    @PrimaryKey
    var currencyCode: String,
    var rate: BigDecimal,
    var timeLastUpdate: String,
    var priorityPosition: Int = 0
)

data class CacheHeaderWithRates(
    @Embedded val cache: CacheRatesHeader,
    @Relation(
        parentColumn = "timeLastUpdate",
        entityColumn = "timeLastUpdate"
    )
    val rates: List<CacheCurrencyRate>
)

fun CacheHeaderWithRates.toDomainModel(): Rates {
    val actualDate: Date = Date(cache.timeLastUpdate)
    val baseCurrency = Currency.getInstance(cache.baseCode)
    val rates: List<Rate> = this.rates.map {
        Rate(
            currency = Currency.getInstance(it.currencyCode),
            value = it.rate,
            flagRes = getFlagRes(it.currencyCode)
        )
    }
    return Rates(actualDate, baseCurrency, rates)
}