package ru.okcode.currencyconverter.data.db.config

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.okcode.currencyconverter.data.model.ConfiguredCurrency

@Entity
data class ConfigEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Long = 777,
    var baseCurrencyCode: String,
    var baseCurrencyAmount: Float,
    var configuredCurrencies: List<ConfiguredCurrency>
) {
    companion object {
        fun createDefaultConfig(): ConfigEntity {
            return ConfigEntity(
                baseCurrencyCode = "EUR",
                baseCurrencyAmount = 1f,
                configuredCurrencies = getDefaultConfiguredCurrencies()
            )
        }

        private fun getDefaultConfiguredCurrencies(): List<ConfiguredCurrency> {
            TODO("Not yet implemented")
        }
    }
}

