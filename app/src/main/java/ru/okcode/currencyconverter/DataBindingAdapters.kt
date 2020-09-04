package ru.okcode.currencyconverter

import android.icu.math.BigDecimal
import android.icu.util.Currency
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.okcode.currencyconverter.util.convertUnixToDateString

@BindingAdapter("srcVector")
fun ImageView.setVectorResource(resource: Int) {
    setImageResource(resource)
}

@BindingAdapter("time")
fun TextView.setTime(timeUnix: Long) {
    text = timeUnix.convertUnixToDateString()
}

@BindingAdapter("rateToBase", "baseCurrency")
fun TextView.setCurrencyRate(rateToBase: BigDecimal, baseCurrency: Currency) {
    TODO()
}