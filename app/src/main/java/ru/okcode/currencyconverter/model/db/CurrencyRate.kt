package ru.okcode.currencyconverter.model.db

import androidx.room.*
import java.util.*

@Entity
data class RateList(
    @PrimaryKey(autoGenerate = true)
    val ratesId: Long,

    @ColumnInfo(name = "rates_date")
    var ratesDate: Date
)

@Entity
data class CurrencyRate(
    @PrimaryKey(autoGenerate = true)
    val rateId: Long,

    @Embedded
    val currency: Currency,

    @ColumnInfo(name = "rate_to_euro")
    var RateToEuro: Double,

    val hostRateList: Long
)

@Entity
data class Currency(
    @PrimaryKey(autoGenerate = true)
    val currencyId: Long,

    @ColumnInfo(name = "short_name")
    val shortName: String,

    @ColumnInfo(name = "flag_and_name_id")
    val flagAndNameId: Long

)

@Entity
data class FlagAndName(
    @PrimaryKey(autoGenerate = true)
    val flagAndNameId: Long
)