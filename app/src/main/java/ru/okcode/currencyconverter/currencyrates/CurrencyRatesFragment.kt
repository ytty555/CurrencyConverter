package ru.okcode.currencyconverter.currencyrates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.databinding.FragmentCurrencyRatesBinding

class CurrencyRatesFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyRatesBinding
    private val viewModel: CurrencyRatesViewModel by activityViewModels()
    private lateinit var ratesRecyclerVeiw: RecyclerView
    private lateinit var coordinatorLayout: CoordinatorLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_currency_rates, container, false)

        binding.lifecycleOwner = this
        coordinatorLayout = binding.coordinator

        binding.viewModel = viewModel


        viewModel.errorManager.observe(viewLifecycleOwner, Observer {
            showMessage("Error: $it")
        })

        // RecyclerView Rates
        val ratesLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        val ratesAdaptor = CurrencyRecyclerViewAdaptor()
        ratesRecyclerVeiw = binding.currencyRatesRecycleview
        ratesRecyclerVeiw.layoutManager = ratesLayoutManager
        ratesRecyclerVeiw.adapter = ratesAdaptor
        viewModel.ratesData.observe(viewLifecycleOwner, Observer {
            it?.let {
                ratesAdaptor.submitList(it.getRatesList())
                ratesAdaptor.notifyDataSetChanged()
            }
        })

        return binding.root
    }

    private fun showMessage(text: String) {
        Snackbar.make(coordinatorLayout, text, Snackbar.LENGTH_SHORT)
            .setDuration(3000)
            .show()
    }
}
