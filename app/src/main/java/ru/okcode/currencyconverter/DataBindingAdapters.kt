package ru.okcode.currencyconverter

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("srcVector")
fun ImageView.setVectorResource(resource: Int) {
    setImageResource(resource)
}