package ru.okcode.currencyconverter.ui.editcurrencylist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.data.model.ConfiguredCurrency
import timber.log.Timber

class AddCurrenciesAdapter(private val eventListener: EventListener) :
    RecyclerView.Adapter<AddCurrenciesAdapter.ViewHolder>() {

    private var currencies: List<ConfiguredCurrency> = emptyList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemBackground =
            itemView.findViewById<ConstraintLayout>(R.id.add_item)

        private val checkBox =
            itemView.findViewById<MaterialCheckBox>(R.id.visibility_checkbox)

        private val flag =
            itemView.findViewById<ImageView>(R.id.currency_flag)

        private val currencyCode =
            itemView.findViewById<TextView>(R.id.currency_code)

        private val currencyName =
            itemView.findViewById<TextView>(R.id.add_currency_name)

        fun bind(configuredCurrency: ConfiguredCurrency, eventListener: EventListener) {
            configuredCurrency.flagRes?.let {
                flag.setImageResource(it)
            }
            if (configuredCurrency.isVisible) {
                itemBackground.setBackgroundColor(Color.argb(100, 224, 255, 255))
            } else {
                itemBackground.setBackgroundColor(Color.argb(10, 255, 160, 122))
            }
            currencyCode.text = configuredCurrency.currencyCode
            currencyName.text = configuredCurrency.currencyName

            checkBox.setOnCheckedChangeListener(null)

            checkBox.isChecked = configuredCurrency.isVisible

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                Timber.d("isChecked")
                configuredCurrency.isVisible = isChecked
                if (configuredCurrency.isVisible) {
                    itemBackground.setBackgroundColor(Color.argb(100, 224, 255, 255))
                } else {
                    itemBackground.setBackgroundColor(Color.argb(10, 255, 160, 122))
                }
                eventListener.onCheckCurrency(configuredCurrency.currencyCode, isChecked)
            }

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
        holder.bind(currencies[position], eventListener)
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

    interface EventListener {
        fun onCheckCurrency(currencyCode: String, isVisible: Boolean)
    }
}