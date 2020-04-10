package ru.okcode.currencyconverter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.okcode.currencyconverter.databinding.RateItemBinding
import ru.okcode.currencyconverter.model.CurrencyRateDto

class RatesListAdapter(dataSet: List<CurrencyRateDto>) :
    RecyclerView.Adapter<RatesListAdapter.RatesViewHolder>() {

    private lateinit var binding: RateItemBinding
    var dataSet: List<CurrencyRateDto> = dataSet
        set(value) {
            field = value
        }

    class RatesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder {

        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.rate_item, parent, false)
        return RatesViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        val item = dataSet.get(position)
        binding.apply {
            currencyCode.text = item.currency.name
            currencyName.setText(item.currency.resName)
            currencyRate.text = item.rateToBaseCurrency.toString()
            currencyFlag.setBackgroundResource(item.currency.resFlag)
        }
    }
}