package ru.okcode.currencyconverter.model.db.config

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Config(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val baseCurrencyCode: String,
    val baseCurrencyAmount: Double
)

@Entity
data class VisibleCurrency(
    @PrimaryKey
    val currencyCode: String,
    val ownerId: Long
)

data class ConfigCurrencies(
    @Embedded val config: Config,
    @Relation(
        parentColumn = "id",
        entityColumn = "ownerId"
    )
    val visibleCurrencyList: List<VisibleCurrency>
)