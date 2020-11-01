package ru.okcode.currencyconverter.data.db.config

import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.ConfiguredCurrency
import ru.okcode.currencyconverter.data.model.ModelMapper
import ru.okcode.currencyconverter.util.getFlagRes
import java.util.*
import javax.inject.Inject

class ConfigMapper @Inject constructor() : ModelMapper<ConfigHeaderWithCurrencies, Config> {
    override fun mapToModel(entity: ConfigHeaderWithCurrencies?): Config? {
        if (entity == null) {
            return null
        }

        val configuredCurrencies: List<ConfiguredCurrency> =
            entity.currencies.map { configCurrency ->
                val currency = Currency.getInstance(configCurrency.currencyCode)
                ConfiguredCurrency(
                    currencyCode = currency.currencyCode,
                    currencyName = currency.displayName,
                    flagRes = getFlagRes(currency.currencyCode),
                    positionInList = configCurrency.position,
                    isVisible = configCurrency.isVisible
                )
            }

        return Config(
            baseCurrencyCode = entity.configHeader.baseCurrencyCode,
            baseCurrencyAmount = entity.configHeader.baseCurrencyAmount,
            configuredCurrencies = configuredCurrencies
        )
    }

    override fun mapToEntity(model: Config): ConfigHeaderWithCurrencies {
        val configHeader = ConfigHeader(
            baseCurrencyCode = model.baseCurrencyCode,
            baseCurrencyAmount = model.baseCurrencyAmount
        )

        val currencies: List<ConfigCurrency> =
            model.configuredCurrencies.map { configuredCurrency ->
                ConfigCurrency(
                    currencyCode = configuredCurrency.currencyCode,
                    position = configuredCurrency.positionInList,
                    isVisible = configuredCurrency.isVisible
                )
            }

        return ConfigHeaderWithCurrencies(
            configHeader = configHeader,
            currencies = currencies
        )
    }
}