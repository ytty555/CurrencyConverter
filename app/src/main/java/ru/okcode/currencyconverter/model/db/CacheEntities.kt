package ru.okcode.currencyconverter.model.db

import android.icu.math.BigDecimal
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Cache(
    @PrimaryKey val timeLastUpdate: String,
    val timeNextUpdate: String,
    val baseCode: String,
)

@Entity
data class CurrencyRate(
    @PrimaryKey val currencyCode: String,
    val rate: BigDecimal,
    val timeLastUpdate: String,
    val priorityPosition: Int
)

data class CacheRates(
    @Embedded val cache: Cache,
    @Relation(
        parentColumn = "timeLastUpdate",
        entityColumn = "timeLastUpdate"
    )
    val rates: List<CurrencyRate>
)

