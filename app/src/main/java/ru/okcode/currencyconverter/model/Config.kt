package ru.okcode.currencyconverter.model

data class Config(
    val baseCurrencyCode: String,
    val baseCurrencyAmount: Double,
    val visibleCurrencies: List<String>
) {
    companion object {
        fun getDefaultConfig(): Config {
            return Config(
                baseCurrencyCode = "EUR",
                baseCurrencyAmount = 1.0,
                visibleCurrencies = ArrayList()
            )
        }
    }
}
