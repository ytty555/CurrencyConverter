package ru.okcode.currencyconverter.data.db.ready

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class ReadyHeader(
    val baseCurrencyCode: String,
    val baseCurrencyAmount: Float,
    val baseCurrencyRateToEuro: Float,
    @PrimaryKey(autoGenerate = false)
    val timeLastUpdateUnix: Long,
    val timeNextUpdateUnix: Long
)

@Entity
data class ReadyRate(
    @PrimaryKey(autoGenerate = false)
    val currencyCode: String,
    val rateToBase: Float,
    val rateToEur: Float,
    val sum: Float,
    val priorityPosition: Int,
    val flagRes: Int?,
    val isVisible: Boolean,
    val timeLastUpdateUnix: Long,
)

data class ReadyHeaderWithRates(
    @Embedded val readyHeader: ReadyHeader,
    @Relation(
        parentColumn = "timeLastUpdateUnix",
        entityColumn = "timeLastUpdateUnix"
    )
    val rates: List<ReadyRate>
)