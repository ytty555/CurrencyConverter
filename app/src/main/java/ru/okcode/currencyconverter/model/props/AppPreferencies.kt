package ru.okcode.currencyconverter.model.props

import android.content.Context

class AppPreferencies(private val context: Context) {

    private val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    var prefsBaseCurrencyCode: String
        get() = prefs.getString(KEY_BASE_CURRENCY_CODE, BASE_CURRENCY_CODE_DEFAULT_VALUE)!!
        set(value) = prefs.edit().putString(KEY_BASE_CURRENCY_CODE, value).apply()

    var prefsBaseCurrencyAmount: Float
        get() = prefs.getFloat(KEY_BASE_CURRENCY_AMOUNT, BASE_CURRENCY_AMOUNT_DEFAULT_VALUE)
        set(value) = prefs.edit().putFloat(KEY_BASE_CURRENCY_AMOUNT, value).apply()

    companion object {
        private const val PREFS = "app_props"
        private const val KEY_BASE_CURRENCY_CODE = "base_currency_code"
        private const val KEY_BASE_CURRENCY_AMOUNT = "base_currency_amount"
        private const val BASE_CURRENCY_CODE_DEFAULT_VALUE = "EUR"
        private const val BASE_CURRENCY_AMOUNT_DEFAULT_VALUE = 1.0F
    }

}


