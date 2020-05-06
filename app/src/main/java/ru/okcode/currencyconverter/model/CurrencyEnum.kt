package ru.okcode.currencyconverter.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.okcode.currencyconverter.R

enum class CurrencyEnum(
    @StringRes fullNameRes: Int,
    @DrawableRes flagRes: Int) {

    RUB(R.drawable.ic_rub, R.string.RUB),
    EUR(R.drawable.ic_eur, R.string.EUR),
    USD(R.drawable.ic_usd, R.string.USD)

}