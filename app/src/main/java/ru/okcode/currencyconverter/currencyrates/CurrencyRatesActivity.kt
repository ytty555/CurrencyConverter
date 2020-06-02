package ru.okcode.currencyconverter.currencyrates

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.databinding.ActivityCurrencyRatesBinding

class CurrencyRatesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurrencyRatesBinding
    private val viewModel: CurrencyRatesViewModel by viewModels()
    private lateinit var ratesRecyclerVeiw: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_rates)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_currency_rates
        )

        binding.lifecycleOwner = this


        // RecyclerView Rates
        val ratesLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        val ratesAdaptor = CurrencyRecyclerViewAdaptor()
        ratesRecyclerVeiw = binding.currencyRatesRecycleview
        ratesRecyclerVeiw.layoutManager = ratesLayoutManager
        ratesRecyclerVeiw.adapter = ratesAdaptor
        viewModel.ratesData.observe(this, Observer {
            it?.let {
                ratesAdaptor.submitList(it.getRatesList())
            }
        })


    }
}
