package ru.okcode.currencyconverter

import android.graphics.Color
import android.icu.math.BigDecimal
import android.icu.util.Currency
import android.view.View
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

@BindingAdapter("currentCurrency", "backgroundFromBaseCurrency")
fun setBackgroundColor(view: View, currentCurrency: Currency, baseCurrency: Currency) {
    if (currentCurrency == baseCurrency) {
        view.setBackgroundColor(Color.parseColor("#FFEACC"))
    } else {
        view.setBackgroundColor(Color.parseColor("#FFFFFF"))
    }
}

@BindingAdapter("currentCurrency", "visibilityFromBaseCurrency")
fun setVisibilityFromBaseCurrency(view: View, currentCurrency: Currency, baseCurrency: Currency) {
    if (currentCurrency == baseCurrency) {
        view.visibility = View.INVISIBLE
    } else {
        view.visibility = View.VISIBLE
    }
}