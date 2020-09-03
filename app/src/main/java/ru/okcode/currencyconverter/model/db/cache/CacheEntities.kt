package ru.okcode.currencyconverter.model.db.cache

import android.icu.math.BigDecimal
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

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