package ru.okcode.currencyconverter.ui.editcurrencylist

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.data.model.ConfiguredCurrency
import ru.okcode.currencyconverter.data.model.changeVisibilityAndPositionBy
import ru.okcode.currencyconverter.data.model.sort
import ru.okcode.currencyconverter.mvibase.MviView
import ru.okcode.currencyconverter.ui.RatesListActivity
import ru.okcode.currencyconverter.ui.editcurrencylist.AddCurrenciesDialogFragment.AddCurrenciesDialogListener
import ru.okcode.currencyconverter.ui.editcurrencylist.EditCurrenciesListIntent.*
import timber.log.Timber

@AndroidEntryPoint
class EditCurrenciesListActivity : AppCompatActivity(),
    MviView<EditCurrenciesListIntent, EditCurrenciesListViewState>,
    EditListListener,
    AddCurrenciesDialogListener {

    // Temp result while editing
    private var tempCurrenciesWhileEditing: List<ConfiguredCurrency> = emptyList()

    private val viewModel: EditCurrenciesListViewModel by viewModels()
    private val disposables = CompositeDisposable()

    // Intents subjects
    private val addPublisher =
        PublishSubject.create<AddCurrencyIntent>()

    private val saveCurrenciesToConfigPublisher =
        PublishSubject.create<SaveCurrenciesToConfigIntent>()

    // View elements
    private lateinit var coordinatorLayout: CoordinatorLayout

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: EditCurrenciesListAdapter

    /**
     * onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_currencies_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        coordinatorLayout = findViewById(R.id.edit_list_coordinator)

        // RecyclerView Edit currencies list
        recyclerView = findViewById(R.id.currencies_recyclerview)
        adapter = EditCurrenciesListAdapter(this)

        val callback: ItemTouchHelper.Callback = EditItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)

        val layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        // FAB
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            saveCurrenciesToConfigPublisher.onNext(
                SaveCurrenciesToConfigIntent(
                    tempCurrenciesWhileEditing
                )
            )
            val dialog: DialogFragment = AddCurrenciesDialogFragment(this)
            dialog.show(supportFragmentManager, "addCurrenciesDialog")
        }
    }

    /**
     * onStart
     */
    override fun onStart() {
        super.onStart()
        disposables.add(viewModel.states().subscribe(this::render))
        viewModel.processIntents(intents())
    }

    /**
     * onStop
     */
    override fun onStop() {
        super.onStop()
        disposables.clear()
    }

    /**
     * onOptionsItemSelected
     */
    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                if (tempCurrenciesWhileEditing.isNotEmpty()) {
                    saveCurrenciesToConfigPublisher.onNext(
                        SaveCurrenciesToConfigIntent(tempCurrenciesWhileEditing)
                    )
                }
                navigateUpTo((Intent(this, RatesListActivity::class.java)))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    /**
     * intents
     */
    override fun intents(): Observable<EditCurrenciesListIntent> {
        return Observable.merge(
            loadFromConfigIntent(),
            addToListIntent(),
            saveToConfigIntent()
        )
    }

    private fun loadFromConfigIntent(): Observable<LoadCurrenciesFromConfigIntent> {
        return Observable.just(LoadCurrenciesFromConfigIntent)
    }

    private fun addToListIntent(): Observable<AddCurrencyIntent> {
        return addPublisher
    }

    private fun saveToConfigIntent(): Observable<SaveCurrenciesToConfigIntent> {
        return saveCurrenciesToConfigPublisher
    }

    /**
     * render
     */
    override fun render(state: EditCurrenciesListViewState) {
        if (state.error != null) {
            renderError(state.error)
        } else {
            renderData(state.currencies)
        }
    }

    private fun renderError(error: Throwable) {
        Timber.d("renderError")
        Snackbar.make(
            coordinatorLayout,
            error.localizedMessage ?: "Unknown error",
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun renderData(currencies: List<ConfiguredCurrency>) {
        Timber.d("renderData $currencies")

        if (tempCurrenciesWhileEditing.isEmpty()) {
            tempCurrenciesWhileEditing = currencies
        }

        adapter.setCurrencies(currencies.filter { it.isVisible }.sort())
    }

    override fun onChangeCurrenciesList(visibleCurrencies: List<ConfiguredCurrency>) {
        tempCurrenciesWhileEditing.changeVisibilityAndPositionBy(visibleCurrencies)
    }

    override fun onPositiveButtonClick(dialog: DialogFragment) {
        TODO("Not yet implemented")
    }
}
