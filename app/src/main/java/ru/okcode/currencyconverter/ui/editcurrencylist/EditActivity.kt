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
import kotlinx.android.synthetic.main.activity_edit_currencies_list.*
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.data.model.ConfiguredCurrency
import ru.okcode.currencyconverter.data.model.changeVisibilityAndPositionBy
import ru.okcode.currencyconverter.mvibase.MviView
import ru.okcode.currencyconverter.ui.RatesListActivity
import ru.okcode.currencyconverter.ui.editcurrencylist.EditCurrenciesListIntent.*
import ru.okcode.currencyconverter.util.visible
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class EditActivity : AppCompatActivity(),
    MviView<EditCurrenciesListIntent, EditCurrenciesListViewState>,
    PriorityPositionAdapter.EventListener,
    AddCurrenciesAdapter.EventListener,
    TitleChangeListener {

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
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            saveCurrenciesToConfigPublisher.onNext(SaveCurrenciesToConfigIntent)
            addPublisher.onNext(AddCurrencyIntent)
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
                val fragmentManager = supportFragmentManager
                when (fragmentManager.fragments[0]) {
                    is PriorityPositionFragment -> {
                        if (viewModel.tempCurrenciesWhileEditing.isNotEmpty()) {
                            saveCurrenciesToConfigPublisher.onNext(SaveCurrenciesToConfigIntent)
                        }
                        navigateUpTo((Intent(this, RatesListActivity::class.java)))
                        true
                    }
                    is AddCurrenciesFragment -> {
                        navigator.showPriorityPositionFragment()
                        fab.visible = true
                        true
                    }
                    else -> false
                }
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
        when {
            state.error != null -> {
                renderError(state.error)
            }
            state.changingPriorityPosition -> {
                renderPriorityPosition()
            }
            state.addingCurrencies -> {
                renderAddCurrencies()
            }
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

    private fun renderPriorityPosition() {
        fab.visible = true
        navigator.showPriorityPositionFragment()
    }

    private fun renderAddCurrencies() {
        fab.visible = false
        navigator.showAddCurrenciesFragment()
    }

    override fun onChangePriorityPosition(currencies: List<ConfiguredCurrency>) {
        viewModel.tempCurrenciesWhileEditing.changeVisibilityAndPositionBy(currencies)
    }

    override fun onCheckCurrency(currencyCode: String, isVisible: Boolean) {
        Timber.d("Old: ${viewModel.tempCurrenciesWhileEditing}")
        viewModel.tempCurrenciesWhileEditing.changeVisibilityAndPositionBy(currencyCode, isVisible)
        Timber.d("New: ${viewModel.tempCurrenciesWhileEditing}")
    }

    override fun setTitle(title: String) {
        supportActionBar?.title = title
    }
}
