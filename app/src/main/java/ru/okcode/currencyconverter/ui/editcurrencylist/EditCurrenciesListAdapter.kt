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
import java.util.*

class EditCurrenciesListAdapter(private val listener: EditCurrenciesListListener) :
    RecyclerView.Adapter<EditCurrenciesListAdapter.ViewHolder>(),
    EditListItemTouchHelperApapter {

    private var currencies: MutableList<ConfiguredCurrency> = mutableListOf()

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val priorityPosition = itemView.findViewById<TextView>(R.id.position)
        private val currencyFlag = itemView.findViewById<ImageView>(R.id.currency_flag)
        private val currencyCode = itemView.findViewById<TextView>(R.id.currency_code)
        private val currencyName = itemView.findViewById<TextView>(R.id.currency_name)

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.edit_currency_item, parent, false)
                return ViewHolder(view)
            }
        }

        fun bind(item: ConfiguredCurrency) {
            priorityPosition.text = item.positionInList.toString()
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

    fun setCurrencies(currencies: MutableList<ConfiguredCurrency>) {

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

        this.currencies = currencies

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                listener.onItemMove(currencies[i].currencyCode, i + 1)
                listener.onItemMove(currencies[i + 1].currencyCode, i)
                Collections.swap(currencies, i, i + 1)
            }
        } else {
            for (i in fromPosition until toPosition) {
                listener.onItemMove(currencies[i].currencyCode, i - 1)
                listener.onItemMove(currencies[i - 1].currencyCode, i)
                Collections.swap(currencies, i, i - 1)
            }
        }


        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        currencies.removeAt(position)

        notifyItemRemoved(position)
    }

}
