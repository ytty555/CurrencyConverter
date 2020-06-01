package ru.okcode.currencyconverter.currencyrates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.okcode.currencyconverter.databinding.RateItemBinding
import ru.okcode.currencyconverter.model.api.Rate

class CurrencyRecyclerViewAdaptor :
    ListAdapter<Rate, CurrencyRecyclerViewAdaptor.ViewHolder>(RateDiff()) {


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
        holder.bind(getItem(position))
    }
}

class RateDiff: DiffUtil.ItemCallback<Rate>() {
    override fun areItemsTheSame(oldItem: Rate, newItem: Rate): Boolean {
        return oldItem.currencyCode == newItem.currencyCode
    }

    override fun areContentsTheSame(oldItem: Rate, newItem: Rate): Boolean {
        return oldItem == newItem
    }

}

