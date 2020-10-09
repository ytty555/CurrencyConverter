package ru.okcode.currencyconverter.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.data.repository.ConfigRepository
import ru.okcode.currencyconverter.ui.overview.OverviewFragment
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var configRepository: ConfigRepository
    @Inject lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycle.addObserver(configRepository)

        navigator.activity = this

        if (savedInstanceState == null) {
            navigator.showOverview()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.activity = null
    }
}