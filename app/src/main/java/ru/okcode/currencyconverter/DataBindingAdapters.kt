package ru.okcode.currencyconverter

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import ru.okcode.currencyconverter.ui.overview.ApiStatus

@BindingAdapter("srcVector")
fun ImageView.setVectorResource(resource: Int) {
    setImageResource(resource)
}

@BindingAdapter("loadingApiStatus")
fun bindStatus(statusImageView: ImageView, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
            Log.e("qq", "BindingAdapter bindStatus $status")
        }
        ApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
            Log.e("qq", "BindingAdapter bindStatus $status")
        }
        ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
            Log.e("qq", "BindingAdapter bindStatus $status")
        }
        else -> {
            statusImageView.visibility = View.GONE
            Log.e("qq", "BindingAdapter bindStatus $status")
        }
    }
}