package ru.okcode.currencyconverter.ui

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.data.repository.ConfigRepository
import ru.okcode.currencyconverter.ui.overview.OverviewFragment
import ru.okcode.currencyconverter.ui.overview.OverviewNavigator
import javax.inject.Inject

@AndroidEntryPoint
class RatesListActivity : AppCompatActivity() {

    @Inject
    lateinit var configRepository: ConfigRepository

    @Inject
    lateinit var overviewNavigator: OverviewNavigator

    private var twoPane: Boolean = false

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (findViewById<FrameLayout>(R.id.base_chooser_container) != null) {
            twoPane = true
        }

        lifecycle.addObserver(configRepository)

        overviewNavigator.twoPane = twoPane
        overviewNavigator.activity = this

        if (savedInstanceState == null) {
            val fragment = OverviewFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.overview_container, fragment)
                .commit()
        }

        if (twoPane) {
            showBaseChooser()
        }
    }

    private fun showBaseChooser() {
        val configDisposable = configRepository
            .getConfigSingle()
            .map { config ->
                Pair(config.baseCurrencyCode, config.baseCurrencyAmount)
            }
            .subscribeBy {
                overviewNavigator.showBaseChooser(
                    it.first,
                    it.second)
            }

        disposables.add(configDisposable)
    }

    override fun onStop() {
        disposables.clear()
        super.onStop()
    }
}