package ru.okcode.currencyconverter.ui.overview

import android.icu.util.Currency
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.data.model.Rate
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.util.getFlagRes

class OverviewAdaptor(private val rateListListener: RatesListListener) :
    RecyclerView.Adapter<OverviewAdaptor.ViewHolder>() {

    private var ratesData: Rates = Rates.idle()

    class ViewHolder private constructor(view: View) :
        RecyclerView.ViewHolder(view) {
        val currencyCodeTextView: TextView = itemView.findViewById(R.id.currency_code)
        val currencyNameTextView: TextView = itemView.findViewById(R.id.currency_name)
        val baseCurrencyAmountSymbolEqualTextView: TextView =
            itemView.findViewById(R.id.base_currency_amount_symbol_equal)
        val currencyRateTextView: TextView = itemView.findViewById(R.id.currency_rate)
        val currencySymbolTextView: TextView = itemView.findViewById(R.id.currency_symbol)
        val currencyFlagImageView: ImageView = itemView.findViewById(R.id.currency_flag)

        fun bind(ratesData: Rates, position: Int, rateListListener: RatesListListener) {
            val rate = ratesData.rates[position]
            currencyCodeTextView.text = rate.currency.currencyCode
            currencyNameTextView.text = rate.currency.displayName
            baseCurrencyAmountSymbolEqualTextView.text =
                "${ratesData.baseCurrencyAmount} ${ratesData.baseCurrency.symbol} ="
            currencyRateTextView.text = rate.sum.toString()
            currencySymbolTextView.text = rate.currency.symbol
            rate.currency.getFlagRes()?.let { currencyFlagImageView.setImageResource(it) }
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

class RatesListListener(val clickListener: (currency: Currency) -> Unit) {
    fun onClick(rate: Rate) = clickListener(rate.currency)
}
