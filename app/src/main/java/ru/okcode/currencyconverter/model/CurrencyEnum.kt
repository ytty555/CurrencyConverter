package ru.okcode.currencyconverter.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.okcode.currencyconverter.R

enum class CurrencyEnum(
    @DrawableRes val flagRes: Int = R.drawable.ic_eur, // TODO fix it!!!!!
    @StringRes val fullNameRes: Int = R.string.noName
) {
    RUB(flagRes = R.drawable.ic_rub, fullNameRes = R.string.RUB),
    EUR(flagRes = R.drawable.ic_eur, fullNameRes = R.string.EUR),
    USD(flagRes = R.drawable.ic_usd, fullNameRes = R.string.USD),
    CAD(),
    HKD(),
    ISK(),
    PHP(),
    DKK(),
    HUF(),
    CZK(),
    AUD(),
    RON(),
    SEK(),
    IDR(),
    INR(),
    BRL(),
    HRK(),
    JPY(),
    THB(),
    CHF(),
    SGD(),
    PLN(),
    BGN(),
    TRY(),
    CNY(),
    NOK(),
    NZD(),
    ZAR(),
    MXN(),
    ILS(),
    GBP(),
    KRW(),
    MYR()
}