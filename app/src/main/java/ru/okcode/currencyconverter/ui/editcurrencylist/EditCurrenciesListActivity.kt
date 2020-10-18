package ru.okcode.currencyconverter.ui.editcurrencylist

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.mvibase.MviView
import ru.okcode.currencyconverter.ui.RatesListActivity

@AndroidEntryPoint
class EditCurrenciesListActivity : AppCompatActivity(),
    MviView<EditCurrenciesListIntent, EditCurrenciesListViewState> {

    private val viewModel: EditCurrenciesListViewModel by viewModels()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_currencies_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // RecyclerView Edit currencies list
        val recyclerView: RecyclerView = findViewById(R.id.currencies_recyclerview)
        val adapter = EditCurrenciesListAdapter()
        val layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        // FAB
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onStart() {
        super.onStart()
        disposables.add(viewModel.states().subscribe(this::render))
        viewModel.processIntents(intents())
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo((Intent(this, RatesListActivity::class.java)))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun intents(): Observable<EditCurrenciesListIntent> {
        TODO("Not yet implemented")
    }

    override fun render(state: EditCurrenciesListViewState) {
        TODO("Not yet implemented")
    }
}
