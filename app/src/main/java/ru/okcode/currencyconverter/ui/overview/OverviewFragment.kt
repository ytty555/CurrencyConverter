package ru.okcode.currencyconverter.ui.overview

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_currency_rates.*
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.mvibase.MviView
import ru.okcode.currencyconverter.ui.Destinations
import ru.okcode.currencyconverter.ui.FragmentHelper
import ru.okcode.currencyconverter.ui.basechooser.BaseChooserFragment
import ru.okcode.currencyconverter.util.visible

@AndroidEntryPoint
class OverviewFragment : Fragment(), MviView<OverviewIntent, OverviewViewState>, OverviewListener {

    private val viewModel: OverviewViewModel by viewModels()
    private val disposables = CompositeDisposable()
    private val adaptor = OverviewAdaptor(this)

    private val changeBaseCurrencySubject =
        PublishSubject.create<OverviewIntent.ChangeBaseCurrencyIntent>()

    private val editRatesListSubject =
        PublishSubject.create<OverviewIntent.EditCurrencyListIntent>()

    private val updateRawRatesSubject =
        PublishSubject.create<OverviewIntent.UpdateRawRatesIntent>()


    override fun onStart() {
        super.onStart()
        bind()
        updateRawRatesSubject.onNext(OverviewIntent.UpdateRawRatesIntent)
    }

    private fun bind() {
        disposables.add(viewModel.states().subscribe(this::render))
        viewModel.processIntents(intents())
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overview_rates_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit_currency_list -> {
                editRatesListSubject.onNext(OverviewIntent.EditCurrencyListIntent)
                true
            }
            R.id.update_rates -> {
                updateRawRatesSubject.onNext(OverviewIntent.UpdateRawRatesIntent)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_currency_rates, container, false)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)

        // RecyclerView Rates
        val ratesLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        val recyclerView: RecyclerView = view.findViewById(R.id.rates_recyclerview)
        recyclerView.layoutManager = ratesLayoutManager
        recyclerView.adapter = adaptor

        return view
    }

    override fun intents(): Observable<OverviewIntent> {
        return Observable.merge(
            updateRawRatesIntent(),
            editCurrencyListIntent(),
            changeBaseCurrencyIntent()
        )
    }


    private fun updateRawRatesIntent(): Observable<OverviewIntent.UpdateRawRatesIntent> {
        return updateRawRatesSubject
    }

    private fun editCurrencyListIntent(): Observable<OverviewIntent.EditCurrencyListIntent> {
        return editRatesListSubject
    }

    private fun changeBaseCurrencyIntent(): Observable<OverviewIntent.ChangeBaseCurrencyIntent> {
        return changeBaseCurrencySubject
    }


    override fun render(state: OverviewViewState) {

        loading_data.visible = state.isLoading

        if (state.isLoading) {
            error_data.visible = false
        }

        if (state.rates.rates.isNotEmpty()) {
            loading_data.visible = false
            error_data.visible = false
            adaptor.setData(state.rates)
            rates_data.visible = true
        }

        if (!state.message.isNullOrEmpty()) {
            showMessage(state.message)
        }

        if (state.error != null) {
            loading_data.visible = false
            rates_data.visible = false
            error_data.visible = true

            showMessage(state.error.localizedMessage ?: "111111111111111111111111111111111")
        }

        if (state.switchingTo != null) {
            when (state.switchingTo) {
                is Destinations.ChangeBaseCurrencyDestination -> {
                    val argCurrencyCode = state.switchingTo.currencyCode
                    val argCurrencyAmount = state.switchingTo.currentCurrencyAmount

                    val fragment =
                        BaseChooserFragment.newInstance(argCurrencyCode, argCurrencyAmount)

                    FragmentHelper.replace(
                        fragmentActivity = requireActivity(),
                        fragment = fragment
                    )
                }
                is Destinations.EditCurrencyListDestination -> {
                    Toast.makeText(activity, "Click Edit rates list", Toast.LENGTH_SHORT).show()
                }
                is Destinations.OverviewRatesDestination -> {
                    Toast.makeText(activity, "Click OverviewRatesDestination", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            activity,
            message,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onClickRateItem(currencyCode: String, currencyAmount: Float) {
        changeBaseCurrencySubject.onNext(
            OverviewIntent.ChangeBaseCurrencyIntent(
                currencyCode,
                currencyAmount
            )
        )
    }

    companion object {
        fun newInstance(): OverviewFragment {
            return OverviewFragment()
        }
    }
}
