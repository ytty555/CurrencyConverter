package ru.okcode.currencyconverter.ui.overview

import android.icu.util.Currency
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.util.visible
import kotlin.math.round

class OverviewAdaptor(private val rateListListener: OverviewListener) :
    RecyclerView.Adapter<OverviewAdaptor.ViewHolder>() {

    private var ratesData: Rates = Rates.idle()

    class ViewHolder private constructor(view: View) :
        RecyclerView.ViewHolder(view) {
        private val baseCurrencyIndicator =
            view.findViewById<CardView>(R.id.base_currency_indicator)
        private val currencyCodeTextView = view.findViewById<TextView>(R.id.currency_code)
        private val currencyNameTextView = view.findViewById<TextView>(R.id.currency_name)
        private val currencyRateTextView = view.findViewById<TextView>(R.id.currency_rate)
        private val currencySymbolTextView = view.findViewById<TextView>(R.id.currency_symbol)
        private val baseCurrencyAmountSymbolEqualTextView =
            view.findViewById<TextView>(R.id.base_currency_amount_symbol_equal)
        private val currencyFlagImageView = view.findViewById<ImageView>(R.id.currency_flag)


        fun bind(ratesData: Rates, position: Int, rateListListener: OverviewListener) {
            val rate = ratesData.rates[position]

            itemView.setOnClickListener {
                rateListListener.onClickRateItem(rate.currencyCode, 0f)
            }
            val currency = Currency.getInstance(rate.currencyCode)
            val baseCurrency = Currency.getInstance(ratesData.baseCurrencyCode)

            baseCurrencyIndicator.visible = (rate.currencyCode == ratesData.baseCurrencyCode)

            currencyCodeTextView.text = currency.currencyCode
            currencyNameTextView.text = currency.displayName.capitalize()
            currencyRateTextView.text = (round(rate.sum * 10000) / 10000).toString()
            currencySymbolTextView.text = currency.symbol
            baseCurrencyAmountSymbolEqualTextView.text =
                "${ratesData.baseCurrencyAmount} ${baseCurrency.symbol} ="
            rate.flagRes?.let { currencyFlagImageView.setImageResource(it) }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.rate_item, parent, false)
                return ViewHolder(view)
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

    fun setData(newRates: Rates) {
        val oldRatesList = ratesData.rates
        val newRatesList = newRates.rates

        val diffCallback = object : DiffUtil.Callback() {

            override fun getOldListSize(): Int = oldRatesList.size

            override fun getNewListSize(): Int = newRatesList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldRatesList[oldItemPosition].currencyCode ==
                        newRatesList[newItemPosition].currencyCode
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldRatesList[oldItemPosition] == newRatesList[newItemPosition]
            }

        }

        val diff = DiffUtil.calculateDiff(diffCallback)

        ratesData = newRates

        diff.dispatchUpdatesTo(this)
    }

}
