package ru.okcode.currencyconverter.ui.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.okcode.currencyconverter.databinding.RateItemBinding
import ru.okcode.currencyconverter.model.Rate
import ru.okcode.currencyconverter.model.Rates

class OverviewAdaptor : RecyclerView.Adapter<OverviewAdaptor.ViewHolder>() {

    class ViewHolder private constructor(private val binding: RateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Rate) {
            binding.rate = item
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
        ratesData?.let {
            holder.bind(it.rates[position])
        }
    }

    override fun getItemCount(): Int {
        return ratesData.rates.size
    }

    fun setRatesData(data: Rates) {
        ratesData = data
    }
}

