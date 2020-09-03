package ru.okcode.currencyconverter.model.db.config

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.okcode.currencyconverter.model.Config
import ru.okcode.currencyconverter.model.ModelMapper

@Entity
data class ConfigEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val baseCurrencyCode: String,
    val baseCurrencyAmount: Double,
    val visibleCurrencies: List<String>
)