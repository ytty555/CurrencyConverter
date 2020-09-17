package ru.okcode.currencyconverter

import android.graphics.Color
import android.icu.math.BigDecimal
import android.icu.util.Currency
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.okcode.currencyconverter.data.repository.Status
import ru.okcode.currencyconverter.util.convertUnixToDateString
import kotlin.math.round

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

@BindingAdapter("sum")
fun TextView.displaySum(sum: BigDecimal) {
    val sumAdDouble = sum.toDouble()
    val sumRounded = round(sumAdDouble * 100000) / 100000
    text = sumRounded.toString()
}

@BindingAdapter("symbol", "amount")
fun TextView.displayAmountSymbolEqual(symbol: String, amount: Float) {
    var amountAsString = amount.toString()

    if (amountAsString.endsWith(".0")) {
        amountAsString = amountAsString.substring(0, amountAsString.lastIndex - 1)
    }

    text = "$amountAsString $symbol ="
}

@BindingAdapter("showWhenLoading")
fun setShowWhenLoading(view: View, status: Status) {
    if (status == Status.LOADING) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("showWhenError")
fun setShowWhenError(view: View, status: Status) {
    if (status == Status.ERROR) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("showWhenDone")
fun setShowWhenDone(view: View, status: Status) {
    if (status == Status.DONE) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}