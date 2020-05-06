package ru.okcode.currencyconverter.currencyrates

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.ServiceLocator
import ru.okcode.currencyconverter.databinding.ActivityCurrencyRatesBinding
import ru.okcode.currencyconverter.model.DefaultRatesRepository
import ru.okcode.currencyconverter.model.RatesRepository
import ru.okcode.currencyconverter.model.api.ApiService
import ru.okcode.currencyconverter.model.api.DefaultApiService
import ru.okcode.currencyconverter.model.db.RatesDao
import ru.okcode.currencyconverter.model.db.RatesDatabase

class CurrencyRatesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurrencyRatesBinding
    private lateinit var ratesRecyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdaptor: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_currency_rates
        )

        binding.lifecycleOwner = this

        val appContext: Context = applicationContext
        val ratesRepository: RatesRepository = ServiceLocator.bindRatesRepository(appContext)
        val viewModelFactory = CurrencyRatesViewModelFactory(ratesRepository = ratesRepository)
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(CurrencyRatesViewModel::class.java)


//        // RecyclerView
//        viewManager = LinearLayoutManager(this)
//
//        viewModel.ratesData.observe(this, Observer { ratesData ->
//            if (ratesData != null) {
//                viewAdaptor = CurrencyRecyclerViewAdaptor(ratesData)
//                ratesRecyclerView = binding.currencyRatesRecycleview.apply {
//                    layoutManager = viewManager
//                    adapter = viewAdaptor
//                }
//
//            }
//        })
    }
}
