package ru.okcode.currencyconverter.model

import androidx.annotation.DrawableRes
import ru.okcode.currencyconverter.R

enum class CurrencyEnum(
    @DrawableRes val flagRes: Int,
) {
    RUB(flagRes = R.drawable.ic_rub),
    EUR(flagRes = R.drawable.ic_eur),
    USD(flagRes = R.drawable.ic_usd),
    CAD(flagRes = R.drawable.ic_cad),
    HKD(flagRes = R.drawable.ic_hkd),
    ISK(flagRes = R.drawable.ic_isk),
    PHP(flagRes = R.drawable.ic_php),
    DKK(flagRes = R.drawable.ic_dkk),
    HUF(flagRes = R.drawable.ic_huf),
    CZK(flagRes = R.drawable.ic_czk),
    AUD(flagRes = R.drawable.ic_aud),
    RON(flagRes = R.drawable.ic_ron),
    SEK(flagRes = R.drawable.ic_sek),
    IDR(flagRes = R.drawable.ic_idr),
    INR(flagRes = R.drawable.ic_inr),
    BRL(flagRes = R.drawable.ic_brl),
    HRK(flagRes = R.drawable.ic_hrk),
    JPY(flagRes = R.drawable.ic_jpy),
    THB(flagRes = R.drawable.ic_thb),
    CHF(flagRes = R.drawable.ic_chf),
    SGD(flagRes = R.drawable.ic_sgd),
    PLN(flagRes = R.drawable.ic_pln),
    BGN(flagRes = R.drawable.ic_bgn),
    TRY(flagRes = R.drawable.ic_try),
    CNY(flagRes = R.drawable.ic_cny),
    NOK(flagRes = R.drawable.ic_nok),
    NZD(flagRes = R.drawable.ic_nzd),
    ZAR(flagRes = R.drawable.ic_zar),
    MXN(flagRes = R.drawable.ic_mxn),
    ILS(flagRes = R.drawable.ic_ils),
    GBP(flagRes = R.drawable.ic_gbp),
    KRW(flagRes = R.drawable.ic_krw),
    MYR(flagRes = R.drawable.ic_myr),
    AED(flagRes = R.drawable.ic_aed),
    ARS(flagRes = R.drawable.ic_ars),
    BSD(flagRes = R.drawable.ic_bsd),
    CLP(flagRes = R.drawable.ic_clp),
    COP(flagRes = R.drawable.ic_cop),
    DOP(flagRes = R.drawable.ic_dop),
    EGP(flagRes = R.drawable.ic_egp),
    FJD(flagRes = R.drawable.ic_fjd),
    GTQ(flagRes = R.drawable.ic_gtq),
    KZT(flagRes = R.drawable.ic_kzt),
    MVR(flagRes = R.drawable.ic_mvr),
    PAB(flagRes = R.drawable.ic_pab),
    PEN(flagRes = R.drawable.ic_pen),
    PKR(flagRes = R.drawable.ic_pkr),
    PYG(flagRes = R.drawable.ic_pyg),
    SAR(flagRes = R.drawable.ic_sar),
    TWD(flagRes = R.drawable.ic_twd),
    UAH(flagRes = R.drawable.ic_uah),
    UYU(flagRes = R.drawable.ic_uyu),

}