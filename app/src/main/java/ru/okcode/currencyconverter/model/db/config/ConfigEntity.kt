package ru.okcode.currencyconverter.model.db.config

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.okcode.currencyconverter.model.Config
import ru.okcode.currencyconverter.model.ModelMapper

@Entity
data class ConfigEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
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

