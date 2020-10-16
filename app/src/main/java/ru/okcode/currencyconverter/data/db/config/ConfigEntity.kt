package ru.okcode.currencyconverter.data.db.config

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ConfigEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Long = 777,
    var baseCurrencyCode: String,
    var baseCurrencyAmount: Float,
    var visibleCurrencies: List<String>
) {
    companion object {
        fun createDefaultConfig(): ConfigEntity {
            return ConfigEntity(
                baseCurrencyCode = "EUR",
                baseCurrencyAmount = 1f,
                visibleCurrencies = ArrayList()
            )
        }
    }
}

