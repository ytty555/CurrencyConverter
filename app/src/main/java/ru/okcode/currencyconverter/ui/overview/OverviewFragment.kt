package ru.okcode.currencyconverter.ui.overview

import android.icu.util.Currency
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_currency_rates.*
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.mvibase.MviView
import ru.okcode.currencyconverter.util.visible
import timber.log.Timber

@AndroidEntryPoint
class OverviewFragment : Fragment(),
    MviView<OverviewIntent, OverviewViewState>, OverviewListener,
    SwipeRefreshLayout.OnRefreshListener {

    private val viewModel: OverviewViewModel by viewModels()
    private val disposables = CompositeDisposable()
    private lateinit var recyclerView: RecyclerView
    private val adaptor = OverviewAdaptor(this)

    private var lastState: OverviewViewState? = null

    private val changeBaseCurrencyPublisher =
        PublishSubject.create<OverviewIntent.ChangeBaseCurrencyIntent>()

    private val editRatesListPublisher =
        PublishSubject.create<OverviewIntent.EditCurrencyListIntent>()

    private val updateRatesPublisher =
        PublishSubject.create<OverviewIntent.UpdateRatesIntent>()

    private val instantiateStatePublisher =
        PublishSubject.create<OverviewIntent.InstantiateStateIntent>()

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var baseInfoBar: ConstraintLayout
    private lateinit var baseInfoCodeAndName: TextView
    private lateinit var baseInfoAmount: TextView
    private lateinit var baseInfoSymbol: TextView


    override fun onStart() {
        super.onStart()
        bind()

        if (lastState == null) {
            updateRatesPublisher.onNext(OverviewIntent.UpdateRatesIntent)
        } else {
            instantiateStatePublisher.onNext(OverviewIntent.InstantiateStateIntent(lastState!!))
        }

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
                editRatesListPublisher.onNext(OverviewIntent.EditCurrencyListIntent)
                true
            }
            R.id.update_rates -> {
                updateRatesPublisher.onNext(OverviewIntent.UpdateRatesIntent)
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

        // BaseInfoBar
        baseInfoBar = view.findViewById(R.id.base_info_bar)
        baseInfoCodeAndName = view.findViewById(R.id.currency_codeAndName)
        baseInfoAmount = view.findViewById(R.id.currency_amount)
        baseInfoSymbol = view.findViewById(R.id.currency_symbol)

        // RecyclerView Rates
        val ratesLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.rates_recyclerview)
        recyclerView.layoutManager = ratesLayoutManager
        recyclerView.adapter = adaptor

        // SwipeRefreshLayout
        swipeRefreshLayout = view.findViewById(R.id.swipe_container)
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.isRefreshing = false

        return view
    }

    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing = true
        updateRatesPublisher.onNext(OverviewIntent.UpdateRatesIntent)
        swipeRefreshLayout.isRefreshing = false
    }

    override fun intents(): Observable<OverviewIntent> {
        return Observable.merge(
            updateRatesIntent(),
            editCurrencyListIntent(),
            changeBaseCurrencyIntent()
        )
    }

    private fun updateRatesIntent(): Observable<OverviewIntent.UpdateRatesIntent> {
        return updateRatesPublisher
    }

    private fun editCurrencyListIntent(): Observable<OverviewIntent.EditCurrencyListIntent> {
        return editRatesListPublisher
    }

    private fun changeBaseCurrencyIntent(): Observable<OverviewIntent.ChangeBaseCurrencyIntent> {
        return changeBaseCurrencyPublisher
    }

    override fun render(state: OverviewViewState) {
        if (state.isLoading) { // if Loading
            renderLoading()
        } else if (state.error != null) { // if Error
            renderError(state.error)
        } else if (state.changeBaseCurrency != null) {// if ChangeBaseCurrency
            renderChangeBaseCurrency()
        } else if (state.editCurrencyList) { // if EditCurrencyList
            renderEditCurrencyList()
        } else {
            renderReadyData(state)
        }

    }

    private fun renderEditCurrencyList() {
        Timber.i("renderEditCurrencyList()")
        // Do nothing
    }

    private fun renderChangeBaseCurrency() {
        Timber.i("renderChangeBaseCurrency()")

        // Do nothing
    }

    private fun renderError(error: Throwable) {
        Timber.i("renderError() ${error.localizedMessage}")

        loading_data.visible = false
        baseInfoBar.visible = false
        swipeRefreshLayout.isRefreshing = false
        rates_data.visible = false
        error_data.visible = true

        showMessage(error.localizedMessage ?: "111111111111111111111111111111111")
    }

    private fun renderReadyData(state: OverviewViewState) {
        Timber.i("renderReadyData()")

        lastState = state.copy(
            isLoading = false,
            error = null,
            changeBaseCurrency = null,
            editCurrencyList = false
        )

        loading_data.visible = false
        swipeRefreshLayout.isRefreshing = false
        baseInfoBar.visible = true
        rates_data.visible = true
        error_data.visible = false

        // Render BaseInfoBar
        val baseCurrency = Currency.getInstance(state.readyData.baseCurrencyCode)
        baseInfoCodeAndName.text = "${baseCurrency.currencyCode} ${baseCurrency.displayName.capitalize()}"
        baseInfoAmount.text = state.readyData.baseCurrencyAmount.toString()
        baseInfoSymbol.text = baseCurrency.symbol

        // Render recyclerView Rates
        if (!state.readyData.rates.isNullOrEmpty()) {
            adaptor.setData(state.readyData)
            recyclerView.post { adaptor.notifyDataSetChanged() }
        }
    }

    private fun renderLoading() {
        Timber.i("renderLoading()")
        loading_data.visible = true
        baseInfoBar.visible = false
        swipeRefreshLayout.isRefreshing = true
        rates_data.visible = false
        error_data.visible = false
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            activity,
            message,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onClickRateItem(currencyCode: String, currencyAmount: Float) {
        Timber.d("onClickRateItem $currencyCode $currencyAmount")
        changeBaseCurrencyPublisher.onNext(
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
