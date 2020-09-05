package ru.okcode.currencyconverter.model.db.config

import ru.okcode.currencyconverter.model.Config
import ru.okcode.currencyconverter.model.ModelMapper
import javax.inject.Inject

class ConfigMapper @Inject constructor() : ModelMapper<ConfigEntity, Config> {
    override fun mapToModel(entity: ConfigEntity?): Config? {
        if (entity == null) {
            return null
        }

        return Config(
            baseCurrencyCode = entity.baseCurrencyCode,
            baseCurrencyAmount = entity.baseCurrencyAmount,
            visibleCurrencies = entity.visibleCurrencies
        )
    }

    override fun mapToEntity(model: Config): ConfigEntity {
        return ConfigEntity(
            baseCurrencyCode = model.baseCurrencyCode,
            baseCurrencyAmount = model.baseCurrencyAmount,
            visibleCurrencies = model.visibleCurrencies
        )
    }
}