package ru.okcode.currencyconverter.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.okcode.currencyconverter.R

enum class CurrencyEnum(
    @DrawableRes val flagRes: Int,
    @StringRes val fullNameRes: Int = R.string.noName
) {
    RUB(flagRes = R.drawable.ic_rub, fullNameRes = R.string.RUB),
    EUR(flagRes = R.drawable.ic_eur, fullNameRes = R.string.EUR),
    USD(flagRes = R.drawable.ic_usd, fullNameRes = R.string.USD),
    CAD(flagRes = R.drawable.ic_cad, fullNameRes = R.string.CAD),
    HKD(flagRes = R.drawable.ic_hkd, fullNameRes = R.string.HKD),
    ISK(flagRes = R.drawable.ic_isk, fullNameRes = R.string.ISK),
    PHP(flagRes = R.drawable.ic_php, fullNameRes = R.string.PHP),
    DKK(flagRes = R.drawable.ic_dkk, fullNameRes = R.string.DKK),
    HUF(flagRes = R.drawable.ic_huf, fullNameRes = R.string.HUF),
    CZK(flagRes = R.drawable.ic_czk, fullNameRes = R.string.CZK),
    AUD(flagRes = R.drawable.ic_aud, fullNameRes = R.string.AUD),
    RON(flagRes = R.drawable.ic_ron, fullNameRes = R.string.RON),
    SEK(flagRes = R.drawable.ic_sek, fullNameRes = R.string.SEK),
    IDR(flagRes = R.drawable.ic_idr, fullNameRes = R.string.IDR),
    INR(flagRes = R.drawable.ic_inr, fullNameRes = R.string.INR),
    BRL(flagRes = R.drawable.ic_brl, fullNameRes = R.string.BRL),
    HRK(flagRes = R.drawable.ic_hrk, fullNameRes = R.string.HRK),
    JPY(flagRes = R.drawable.ic_jpy, fullNameRes = R.string.JPY),
    THB(flagRes = R.drawable.ic_thb, fullNameRes = R.string.THB),
    CHF(flagRes = R.drawable.ic_chf, fullNameRes = R.string.CHF),
    SGD(flagRes = R.drawable.ic_sgd, fullNameRes = R.string.SGD),
    PLN(flagRes = R.drawable.ic_pln, fullNameRes = R.string.PLN),
    BGN(flagRes = R.drawable.ic_bgn, fullNameRes = R.string.BGN),
    TRY(flagRes = R.drawable.ic_try, fullNameRes = R.string.TRY),
    CNY(flagRes = R.drawable.ic_cny, fullNameRes = R.string.CNY),
    NOK(flagRes = R.drawable.ic_nok, fullNameRes = R.string.NOK),
    NZD(flagRes = R.drawable.ic_nzd, fullNameRes = R.string.NZD),
    ZAR(flagRes = R.drawable.ic_zar, fullNameRes = R.string.ZAR),
    MXN(flagRes = R.drawable.ic_mxn, fullNameRes = R.string.MXN),
    ILS(flagRes = R.drawable.ic_ils, fullNameRes = R.string.ILS),
    GBP(flagRes = R.drawable.ic_gbp, fullNameRes = R.string.GBP),
    KRW(flagRes = R.drawable.ic_krw, fullNameRes = R.string.KRW),
    MYR(flagRes = R.drawable.ic_myr, fullNameRes = R.string.MYR)
}