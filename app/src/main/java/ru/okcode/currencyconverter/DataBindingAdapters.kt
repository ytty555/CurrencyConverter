package ru.okcode.currencyconverter

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter

@BindingAdapter("srcVector")
fun ImageView.setVectorResource(resource: Int) {
    setImageResource(resource)
}

@BindingAdapter("android:visibility")
fun ProgressBar.setVisibility(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}