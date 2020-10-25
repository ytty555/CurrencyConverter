package ru.okcode.currencyconverter.ui.editcurrencylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.data.model.ConfiguredCurrency

class AddCurrenciesAdapter : RecyclerView.Adapter<AddCurrenciesAdapter.ViewHolder>() {

    private var currencies: List<ConfiguredCurrency> = emptyList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkBox =
            itemView.findViewById<MaterialCheckBox>(R.id.visibility_checkbox)

        private val flag =
            itemView.findViewById<ImageView>(R.id.currency_flag)

        private val currencyCode =
            itemView.findViewById<TextView>(R.id.currency_code)

        private val currencyName =
            itemView.findViewById<TextView>(R.id.currency_name)

        fun bind(configuredCurrency: ConfiguredCurrency) {
            checkBox.isChecked = configuredCurrency.isVisible
            configuredCurrency.flagRes?.let {
                flag.setImageResource(it)
            }
            currencyCode.text = configuredCurrency.currencyCode
            currencyName.text = configuredCurrency.currencyName
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater: LayoutInflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.add_currencies_item, parent, false)
                return ViewHolder(view)
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
        val newCurrencies = currencies
        val oldCurrencies = this.currencies

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

        this.currencies = newCurrencies

        diffResult.dispatchUpdatesTo(this)
    }


}