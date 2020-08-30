package ru.okcode.currencyconverter.model.db.ready

import android.icu.math.BigDecimal
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class ReadyHeader(
    val baseCurrencyCode: String,
    val baseCurrencyAmount: BigDecimal = BigDecimal(1.0),
    @PrimaryKey val timeLastUpdateUnix: Long,
    val timeNextUpdateUnix: Long
)

@Entity
data class ReadyRate(
    @PrimaryKey val currencyCode: String,
    val rate: BigDecimal,
    val sum: BigDecimal,
    val timeLastUpdateUnix: Long,
    val priorityPosition: Int
)

data class ReadyHeaderWithRates(
    @Embedded val readyHeader: ReadyHeader,
    @Relation(
        parentColumn = "timeNextUpdateUnix",
        entityColumn = "timeLastUpdateUnix"
    )
    val rates: List<ReadyRate>
)