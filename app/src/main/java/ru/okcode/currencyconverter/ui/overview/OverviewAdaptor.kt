package ru.okcode.currencyconverter.ui.overview

import android.icu.util.Currency
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.okcode.currencyconverter.databinding.RateItemBinding
import ru.okcode.currencyconverter.data.model.Rate
import ru.okcode.currencyconverter.data.model.Rates

class OverviewAdaptor(private val rateListListener: RatesListListener) :
    RecyclerView.Adapter<OverviewAdaptor.ViewHolder>() {

    private var ratesData: Rates = Rates.idle()

    class ViewHolder private constructor(private val binding: RateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ratesData: Rates, position: Int, rateListListener: RatesListListener) {
            binding.clickListener = rateListListener
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
            holder.bind(ratesData, position, rateListListener)
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

class RatesListListener(val clickListener: (currency: Currency) -> Unit) {
    fun onClick(rate: Rate) = clickListener(rate.currency)
}
