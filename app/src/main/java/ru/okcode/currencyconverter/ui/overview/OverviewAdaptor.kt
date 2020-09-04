package ru.okcode.currencyconverter.ui.overview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.okcode.currencyconverter.databinding.RateItemBinding
import ru.okcode.currencyconverter.model.Rate
import ru.okcode.currencyconverter.model.Rates

private const val TAG = "OverviewAdaptor"

class OverviewAdaptor :
    RecyclerView.Adapter<OverviewAdaptor.ViewHolder>() {

    private var ratesData: Rates = Rates.getEmptyInstance()

    class ViewHolder private constructor(private val binding: RateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ratesData: Rates, position: Int) {
            Log.e(TAG, "ViewHolder bind item: $ratesData.rates[position]")
            binding.fullRates = ratesData
            binding.rate = ratesData.rates[position]
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RateItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!ratesData.rates.isNullOrEmpty()) {
            holder.bind(ratesData, position)
        }
    }

    override fun getItemCount(): Int {
        return if (!ratesData.rates.isNullOrEmpty()) {
            ratesData.rates.size
        } else {
            0
        }
    }

    fun setData(data: Rates) {
        ratesData = data
        notifyDataSetChanged()
    }
}

