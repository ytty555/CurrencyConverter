package ru.okcode.currencyconverter.ui.editcurrencylist

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.data.model.ConfiguredCurrency
import ru.okcode.currencyconverter.data.model.changeVisibilityAndPositionBy
import ru.okcode.currencyconverter.mvibase.MviView
import ru.okcode.currencyconverter.ui.RatesListActivity
import ru.okcode.currencyconverter.ui.editcurrencylist.EditCurrenciesListIntent.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class EditActivity : AppCompatActivity(),
    MviView<EditCurrenciesListIntent, EditCurrenciesListViewState>,
    PriorityPositionAdapter.EventListener {

    // Navigator for fragments
    @Inject
    lateinit var navigator: EditNavigator

    private val viewModel: EditViewModel by viewModels()
    private val disposables = CompositeDisposable()

    // Intents subjects
    private val addPublisher =
        PublishSubject.create<AddCurrencyIntent>()

    private val saveCurrenciesToConfigPublisher =
        PublishSubject.create<SaveCurrenciesToConfigIntent>()

    // View elements
    private lateinit var coordinatorLayout: CoordinatorLayout


    /**
     * onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_currencies_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        coordinatorLayout = findViewById(R.id.edit_list_coordinator)

        // FAB
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            saveCurrenciesToConfigPublisher.onNext(
                SaveCurrenciesToConfigIntent(
                    viewModel.tempCurrenciesWhileEditing
                )
            )
            Snackbar.make(coordinatorLayout, "Add currencies button pressed", Snackbar.LENGTH_LONG)
                .show()
        }
    }

    /**
     * onStart
     */
    override fun onStart() {
        super.onStart()
        disposables.add(viewModel.states().subscribe(this::render))
        viewModel.processIntents(intents())
        navigator.activity = this
    }

    /**
     * onStop
     */
    override fun onStop() {
        super.onStop()
        disposables.clear()
        navigator.activity = null
    }

    /**
     * onOptionsItemSelected
     */
    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                if (viewModel.tempCurrenciesWhileEditing.isNotEmpty()) {
                    saveCurrenciesToConfigPublisher.onNext(
                        SaveCurrenciesToConfigIntent(viewModel.tempCurrenciesWhileEditing)
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
        } else if (state.changingPriorityPosition) {
            renderPriorityPosition(state.currencies)
        } else if (state.addingCurrencies) {
            renderAddCurrencies(state.currencies)
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

    private fun renderPriorityPosition(currencies: List<ConfiguredCurrency>) {
        navigator.showPriorityPositionFragment(currencies)
    }

    private fun renderAddCurrencies(currencies: List<ConfiguredCurrency>) {
        navigator.showAddCurrenciesFragment(currencies)
    }

    override fun onChangePriorityPosition(currencies: List<ConfiguredCurrency>) {
        viewModel.tempCurrenciesWhileEditing.changeVisibilityAndPositionBy(currencies)
    }
}
