package ru.okcode.currencyconverter.data.db.config

import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.ModelMapper
import javax.inject.Inject

class ConfigMapper @Inject constructor() : ModelMapper<ConfigEntity, Config> {
    override fun mapToModel(entity: ConfigEntity?): Config? {
        if (entity == null) {
            return null
        }

        return Config(
            baseCurrencyCode = entity.baseCurrencyCode,
            baseCurrencyAmount = entity.baseCurrencyAmount,
            configuredCurrencies = entity.configuredCurrencies
        )
    }

    override fun mapToEntity(model: Config): ConfigEntity {
        return ConfigEntity(
            baseCurrencyCode = model.baseCurrencyCode,
            baseCurrencyAmount = model.baseCurrencyAmount,
            configuredCurrencies = model.configuredCurrencies
        )
    }
}