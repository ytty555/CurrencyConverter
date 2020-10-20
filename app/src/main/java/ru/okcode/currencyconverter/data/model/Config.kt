package ru.okcode.currencyconverter.data.model

data class Config(
    val baseCurrencyCode: String,
    val baseCurrencyAmount: Float,
    val configuredCurrencies: List<ConfiguredCurrency>
) {
    companion object {
        fun getDefaultConfig(): Config {
            return Config(
                baseCurrencyCode = "EUR",
                baseCurrencyAmount = 1f,
                configuredCurrencies = emptyList()
            )
        }
    }
}
