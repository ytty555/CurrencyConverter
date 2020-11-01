package ru.okcode.currencyconverter.ui.editcurrencylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.okcode.currencyconverter.R

@AndroidEntryPoint
class AddCurrenciesFragment : Fragment() {
    private val viewModel: EditViewModel by activityViewModels()

    private lateinit var titleChangeListener: TitleChangeListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_currencies, container, false)

        // Title
        titleChangeListener = activity as TitleChangeListener
        titleChangeListener.setTitle(resources.getString(R.string.add_title))

        val recyclerView: RecyclerView = view.findViewById(R.id.adding_list)

        val adapter = AddCurrenciesAdapter(requireActivity() as AddCurrenciesAdapter.EventListener)
        val layoutManager = LinearLayoutManager(requireActivity())

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        adapter.setCurrencies(viewModel.tempCurrenciesWhileEditing)

        return view
    }
}