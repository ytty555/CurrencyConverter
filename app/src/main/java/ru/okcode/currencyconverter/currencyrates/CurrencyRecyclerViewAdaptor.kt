package ru.okcode.currencyconverter.currencyrates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.okcode.currencyconverter.databinding.RateItemBinding
import ru.okcode.currencyconverter.model.Rate
import ru.okcode.currencyconverter.model.RatesData

class CurrencyRecyclerViewAdaptor(private val ratesData: RatesData) :
    RecyclerView.Adapter<CurrencyRecyclerViewAdaptor.ViewHolder>() {


    class ViewHolder private constructor(private val binding: RateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rate: Rate) {
            binding.rate = rate
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

    override fun getItemCount(): Int = ratesData.rates.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rate = ratesData.rates[position]
        holder.bind(rate)
    }
}