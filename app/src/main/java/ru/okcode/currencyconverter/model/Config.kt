package ru.okcode.currencyconverter.model

data class Config(
    val baseCurrencyCode: String,
    val baseCurrencyAmount: Float,
    val visibleCurrencies: List<String>
) {
    companion object {
        fun getDefaultConfig(): Config {
            return Config(
                baseCurrencyCode = "EUR",
                baseCurrencyAmount = 1f,
                visibleCurrencies = ArrayList()
            )
        }
    }
}
