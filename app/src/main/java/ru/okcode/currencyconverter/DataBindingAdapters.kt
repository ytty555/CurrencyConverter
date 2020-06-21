package ru.okcode.currencyconverter

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter

@BindingAdapter("app:srcVector")
fun ImageView.setVectorResource(resource: Int) {
    setImageResource(resource)
}

@BindingAdapter("android:visibility")
fun ProgressBar.setVisibility(visible: Boolean) {
    if (visible) {
        visibility = View.VISIBLE
    } else {
        visibility = View.GONE
    }
}