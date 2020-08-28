package ru.okcode.currencyconverter.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.databinding.FragmentCurrencyRatesBinding

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyRatesBinding
    private val viewModel: OverviewViewModel by viewModels()
    private lateinit var ratesRecyclerVeiw: RecyclerView
    private lateinit var coordinatorLayout: CoordinatorLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_currency_rates, container, false)

        binding.lifecycleOwner = this
        coordinatorLayout = binding.coordinator

        binding.viewModel = viewModel

        // Handle messages
        viewModel.message.observe(viewLifecycleOwner, { errorMessage ->
            showMessage(errorMessage)
        })

        // RecyclerView Rates
        val ratesLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        val ratesAdaptor = CurrencyRecyclerViewAdaptor()
        ratesRecyclerVeiw = binding.currencyRatesRecycleview
        ratesRecyclerVeiw.layoutManager = ratesLayoutManager
        ratesRecyclerVeiw.adapter = ratesAdaptor
        viewModel.rates.observe(viewLifecycleOwner, { ratesList ->
            ratesList?.let {
                ratesAdaptor.submitList(ratesList.rates)
                ratesAdaptor.notifyDataSetChanged()
            }
        })

        return binding.root
    }

    private fun showMessage(text: String) {
        Snackbar.make(coordinatorLayout, text, Snackbar.LENGTH_SHORT)
            .setDuration(7000)
            .show()
    }
}
