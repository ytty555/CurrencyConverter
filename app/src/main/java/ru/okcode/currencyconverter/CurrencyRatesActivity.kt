package ru.okcode.currencyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.okcode.currencyconverter.databinding.ActivityCurrencyRatesBinding

class CurrencyRatesActivity : AppCompatActivity() {

    private val TAG: String = "CurrencyRatesActivity"

    private lateinit var recycleView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager


    private lateinit var binding: ActivityCurrencyRatesBinding
    private val viewModel: CurrencyRatesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_currency_rates)
        viewModel.baseCurrency.observe(this, Observer {
            binding.currencyBase.text = getString(R.string.base_currency_info, getString(it.resName))
        })

        binding.changeBaseCurrency.setOnClickListener {
            viewModel.changeBaseCurrency()
        }

        binding.changeRates.setOnClickListener {
            viewModel.changeRates()
        }
        viewManager = LinearLayoutManager(this)

        viewModel.rates.observe(this, Observer {
            Log.i(TAG, "Rates must be changed")
            recycleView = binding.currencyRatesRecycleview
            recycleView.layoutManager = viewManager
            val adapter = RatesListAdapter(it)
            recycleView.adapter = adapter
        })



    }

}
