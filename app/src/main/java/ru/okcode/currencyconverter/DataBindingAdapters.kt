package ru.okcode.currencyconverter

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:srcVector")
fun setImageResource(view: ImageView, resource: Int) {
    view.setImageResource(resource)
}