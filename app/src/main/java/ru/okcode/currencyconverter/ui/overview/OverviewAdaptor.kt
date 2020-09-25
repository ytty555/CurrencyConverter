package ru.okcode.currencyconverter.ui.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.data.model.Rates

class OverviewAdaptor(val rateListListener: OverviewListener) :
    RecyclerView.Adapter<OverviewAdaptor.ViewHolder>() {

    private var ratesData: Rates = Rates.idle()

    class ViewHolder private constructor(view: View) :
        RecyclerView.ViewHolder(view) {

        val currencyCodeTextView = view.findViewById<TextView>(R.id.currency_code)
        val currencyNameTextView = view.findViewById<TextView>(R.id.currency_name)
        val currencyRateTextView = view.findViewById<TextView>(R.id.currency_rate)
        val currencySymbolTextView = view.findViewById<TextView>(R.id.currency_symbol)
        val baseCurrencyAmountSymbolEqualTextView =
            view.findViewById<TextView>(R.id.base_currency_amount_symbol_equal)
        val currencyFlagImageView = view.findViewById<ImageView>(R.id.currency_flag)


        fun bind(ratesData: Rates, position: Int, rateListListener: OverviewListener) {
            val rate = ratesData.rates[position]

            itemView.setOnClickListener {
                rateListListener.onClickRateItem(rate.currency.currencyCode, rate.sum.toFloat())
            }

            currencyCodeTextView.text = rate.currency.currencyCode
            currencyNameTextView.text = rate.currency.displayName
            currencyRateTextView.text = rate.sum.toString()
            currencySymbolTextView.text = rate.currency.symbol
            baseCurrencyAmountSymbolEqualTextView.text =
                "${ratesData.baseCurrencyAmount} ${ratesData.baseCurrency.symbol} ="
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

    fun setData(data: Rates) {
        ratesData = data
        notifyDataSetChanged()
    }
}
