package ru.okcode.currencyconverter.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.okcode.currencyconverter.R

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by viewModels()
    private lateinit var ratesRecyclerView: RecyclerView
    private lateinit var coordinatorLayout: CoordinatorLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // RecyclerView Rates
        val ratesLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        val ratesAdaptor = OverviewAdaptor(RatesListListener { currency ->
            val baseCurrencyCode = currency.currencyCode
            val action =
                OverviewFragmentDirections.actionCurrencyRatesFragmentToBaseChooserFragment(
                    baseCurrencyCode
                )
            findNavController().navigate(action)
        })

        return inflater.inflate(R.layout.fragment_currency_rates, container, false)
    }
}
