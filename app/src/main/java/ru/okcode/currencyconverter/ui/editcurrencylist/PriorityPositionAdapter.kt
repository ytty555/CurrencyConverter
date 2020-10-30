package ru.okcode.currencyconverter.ui.editcurrencylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.data.model.ConfiguredCurrency
import ru.okcode.currencyconverter.data.model.reindexPriorityPosition
import timber.log.Timber
import java.util.*

class PriorityPositionAdapter(private val listener: EventListener) :
    RecyclerView.Adapter<PriorityPositionAdapter.ViewHolder>(),
    PriorityPositionItemTouchHelperAdapter {

    private var currencies = mutableListOf<ConfiguredCurrency>()

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val currencyFlag = itemView.findViewById<ImageView>(R.id.currency_flag)
        private val currencyCode = itemView.findViewById<TextView>(R.id.currency_code)
        private val currencyName = itemView.findViewById<TextView>(R.id.currency_name)

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.priority_position_item, parent, false)
                return ViewHolder(view)
            }
        }

        fun bind(item: ConfiguredCurrency) {
            currencyCode.text = item.currencyCode
            currencyName.text = item.currencyName
            item.flagRes?.let {
                currencyFlag.setImageResource(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currencies[position])
    }

    override fun getItemCount(): Int = currencies.size

    fun setCurrencies(currencies: List<ConfiguredCurrency>) {

        val oldCurrencies = this.currencies
        val newCurrencies = currencies

        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = oldCurrencies.size

            override fun getNewListSize(): Int = newCurrencies.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldCurrencies[oldItemPosition].currencyCode ==
                        newCurrencies[newItemPosition].currencyCode
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldCurrencies[oldItemPosition] == newCurrencies[newItemPosition]
            }

        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.currencies = currencies.toMutableList()

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(currencies, i, i + 1)
                currencies.reindexPriorityPosition()
                listener.onChangePriorityPosition(currencies)
                Timber.d("config change currencies $currencies")
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(currencies, i, i - 1)
                currencies.reindexPriorityPosition()
                listener.onChangePriorityPosition(currencies)
                Timber.d("config change currencies $currencies")
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        currencies.removeAt(position)
        currencies.reindexPriorityPosition()
        listener.onChangePriorityPosition(currencies)
        notifyItemRemoved(position)
    }

    interface EventListener {
        fun onChangePriorityPosition(currencies: List<ConfiguredCurrency>)
    }
}
