package ru.okcode.currencyconverter.ui.overview

import dagger.hilt.android.scopes.ActivityRetainedScoped
import ru.okcode.currencyconverter.ui.Navigator
import javax.inject.Inject

@ActivityRetainedScoped
class OverviewFlowCoordinator @Inject constructor(
    private val navigator: Navigator
) {

    fun start() {
        navigator.showOverview()
    }

    fun showBaseChooser(currencyCode: String, currencyAmount: Float) {
        navigator.showBaseChooser(currencyCode, currencyAmount)
    }

    fun showEditCurrencyList() {
        navigator.showEditCurrencyList()
    }
}