package ru.okcode.currencyconverter.data.db.config

import android.icu.util.Currency
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import ru.okcode.currencyconverter.data.model.CurrencyFlagsStore
import ru.okcode.currencyconverter.util.*
import timber.log.Timber
import java.util.*

@Entity
data class ConfigHeader(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 777,
    var baseCurrencyCode: String,
    var baseCurrencyAmount: Float
)

@Entity
data class ConfigCurrency(
    @PrimaryKey(autoGenerate = false)
    val currencyCode: String,
    val position: Int,
    val isVisible: Boolean,
    val parentId: Int = 777
)

data class ConfigHeaderWithCurrencies(
    @Embedded val configHeader: ConfigHeader,
    @Relation(
        parentColumn = "id",
        entityColumn = "parentId"
    )
    val currencies: List<ConfigCurrency>
) {
    companion object {
        fun createDefaultConfig(): ConfigHeaderWithCurrencies {
            val configHeader = ConfigHeader(
                baseCurrencyCode = EUR_CODE,
                baseCurrencyAmount = 1f
            )

            val currencies: MutableList<ConfigCurrency> = mutableListOf()

            val allCurrencyCodes =
                arrayListOf(CurrencyFlagsStore.values())
                    .flatMap {
                        it.map { currencyFlagStor ->
                            currencyFlagStor.name
                        }
                    }

            val localCurrencyCode: String = Currency.getInstance(Locale.getDefault()).currencyCode

            var startInvisiblePosition = 9

            for (currencyCode in allCurrencyCodes) {
                when (currencyCode) {
                    localCurrencyCode -> {
                        Timber.d("LocalCurrency")
                        addCurrencyToDefaultList(
                            target = currencies,
                            position = 1,
                            currencyCode = currencyCode,
                            isVisible = true
                        )
                    }
                    EUR_CODE -> {
                        addCurrencyToDefaultList(
                            target = currencies,
                            position = 2,
                            currencyCode = currencyCode,
                            isVisible = true
                        )
                    }
                    USD_CODE -> {
                        addCurrencyToDefaultList(
                            target = currencies,
                            position = 3,
                            currencyCode = currencyCode,
                            isVisible = true
                        )
                    }
                    RUB_CODE -> {
                        addCurrencyToDefaultList(
                            target = currencies,
                            position = 4,
                            currencyCode = currencyCode,
                            isVisible = true
                        )
                    }
                    GBP_CODE -> {
                        addCurrencyToDefaultList(
                            target = currencies,
                            position = 5,
                            currencyCode = currencyCode,
                            isVisible = true
                        )
                    }
                    JPY_CODE -> {
                        addCurrencyToDefaultList(
                            target = currencies,
                            position = 6,
                            currencyCode = currencyCode,
                            isVisible = true
                        )
                    }
                    CHF_CODE -> {
                        addCurrencyToDefaultList(
                            target = currencies,
                            position = 7,
                            currencyCode = currencyCode,
                            isVisible = true
                        )
                    }
                    CNY_CODE -> {
                        addCurrencyToDefaultList(
                            target = currencies,
                            position = 8,
                            currencyCode = currencyCode,
                            isVisible = true
                        )
                    }
                    else -> {
                        addCurrencyToDefaultList(
                            target = currencies,
                            position = startInvisiblePosition,
                            currencyCode = currencyCode,
                            isVisible = false
                        )
                    }
                }
                startInvisiblePosition++
            }

            return ConfigHeaderWithCurrencies(configHeader, currencies)
        }

        private fun addCurrencyToDefaultList(
            target: MutableList<ConfigCurrency>,
            position: Int,
            currencyCode: String,
            isVisible: Boolean
        ) {
            target.add(
                ConfigCurrency(
                    currencyCode = currencyCode,
                    position = position,
                    isVisible = isVisible
                )
            )
        }
    }
}

