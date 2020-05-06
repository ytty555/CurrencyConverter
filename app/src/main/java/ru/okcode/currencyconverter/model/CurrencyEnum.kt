package ru.okcode.currencyconverter.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.okcode.currencyconverter.R

enum class CurrencyEnum(
    @DrawableRes val flagRes: Int,
    @StringRes val fullNameRes: Int
) {
    RUB(flagRes = R.drawable.ic_rub, fullNameRes = R.string.RUB),
    EUR(flagRes = R.drawable.ic_eur, fullNameRes = R.string.EUR),
    USD(flagRes = R.drawable.ic_usd, fullNameRes = R.string.USD);
}