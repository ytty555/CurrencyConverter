package ru.okcode.currencyconverter.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.okcode.currencyconverter.R

enum class CurrencyEnum(@StringRes val resName: Int, @DrawableRes val resFlag: Int) {
    RUB(R.string.RUB, R.drawable.ic_rub),
    EUR(R.string.EUR, R.drawable.ic_eur),
    USD(R.string.USD, R.drawable.ic_usd)
}