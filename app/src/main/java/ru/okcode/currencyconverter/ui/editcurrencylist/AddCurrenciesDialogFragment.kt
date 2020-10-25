package ru.okcode.currencyconverter.ui.editcurrencylist

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.data.model.ConfiguredCurrency
import ru.okcode.currencyconverter.data.repository.ConfigRepository
import javax.inject.Inject

@AndroidEntryPoint
class AddCurrenciesDialogFragment(private val listener: AddCurrenciesDialogListener) :
    DialogFragment() {

    @Inject
    lateinit var configRepository: ConfigRepository

    private val disposables = CompositeDisposable()

    private lateinit var tempConfiguredCurrencies: List<ConfiguredCurrency>

    private lateinit var recyclerView: RecyclerView
    private val adapter = AddCurrenciesAdapter()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_currencies, null)

        recyclerView = dialogView.findViewById(R.id.currencies_recyclerview)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager

        builder
            .setView(dialogView)
            .setMessage("Choose currencies to view them")
            .setNegativeButton(R.string.cancel) { dialog, which ->
                Toast.makeText(activity, "Cancel pressed", Toast.LENGTH_SHORT).show()
            }
            .setPositiveButton(R.string.ok) { dialog, which ->
                Toast.makeText(activity, "Ok pressed", Toast.LENGTH_SHORT).show()
            }
        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        val configDisposable = configRepository.getConfigSingle()
            .map { it.configuredCurrencies }
            .subscribeBy(
                onSuccess = {
                    tempConfiguredCurrencies = it
                    adapter.setCurrencies(tempConfiguredCurrencies)
                }
            )
        disposables.add(configDisposable)
    }

    override fun onStop() {
        disposables.clear()
        super.onStop()
    }

    interface AddCurrenciesDialogListener {
        fun onPositiveButtonClick(dialog: DialogFragment)
    }
}