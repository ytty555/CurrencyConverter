package ru.okcode.currencyconverter.ui.editcurrencylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.data.model.sort

@AndroidEntryPoint
class PriorityPositionFragment : Fragment() {
    private val viewModel: EditViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PriorityPositionAdapter

    private lateinit var titleChangeListener: TitleChangeListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_priority_position, container, false)

        // Title
        titleChangeListener = activity as TitleChangeListener
        titleChangeListener.setTitle(resources.getString(R.string.property_position_title))

        // RecyclerView Edit currencies list
        recyclerView = view.findViewById(R.id.currencies_recyclerview)
        adapter =
            PriorityPositionAdapter(requireActivity() as PriorityPositionAdapter.EventListener)

        val callback: ItemTouchHelper.Callback = PriorityPositionItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)

        val layoutManager = LinearLayoutManager(requireActivity())

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        val visibleCurrencies = viewModel.tempCurrenciesWhileEditing.filter { it.isVisible }.sort()
        adapter.setCurrencies(visibleCurrencies)

        return view
    }
}