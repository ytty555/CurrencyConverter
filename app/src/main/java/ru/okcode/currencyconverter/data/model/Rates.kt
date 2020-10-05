package ru.okcode.currencyconverter.data.model

data class Rates(
    val baseCurrencyCode: String,
    val baseCurrencyAmount: Float = 1f,
    val baseCurrencyRateToEuro: Float,
    val rates: List<Rate>,
    val timeLastUpdateUnix: Long,
    val timeNextUpdateUnix: Long
) {
    companion object {
        fun idle(): Rates {
            return Rates(
                baseCurrencyCode = "EUR",
                baseCurrencyAmount = 1f,
                baseCurrencyRateToEuro = 1f,
                timeLastUpdateUnix = 0,
                timeNextUpdateUnix = 0,
                rates = emptyList()
            )
        }
    }
}

data class Rate(
    val currencyCode: String,
    val rateToBase: Float,
    val rateToEur: Float,
    val sum: Float,
    val priorityPosition: Int = 0,
    val flagRes: Int?,
    val isVisible: Boolean = true
)