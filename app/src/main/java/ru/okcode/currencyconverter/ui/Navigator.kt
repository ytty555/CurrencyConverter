package ru.okcode.currencyconverter.ui

import androidx.fragment.app.FragmentActivity
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.ui.basechooser.BaseChooserFragment
import ru.okcode.currencyconverter.ui.editcurrencyset.EditCurrencySetFragment
import ru.okcode.currencyconverter.ui.overview.OverviewFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {
    var activity: FragmentActivity? = null

    companion object {
        private const val CONTAINER = R.id.fragment_container
    }

    fun showOverview() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(CONTAINER, OverviewFragment.newInstance())
            .commit()
    }

    fun showBaseChooser(currencyCode: String, currencyAmount: Float) {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(CONTAINER, BaseChooserFragment.newInstance(currencyCode, currencyAmount))
            .addToBackStack("BaseChooser")
            .commit()
    }

    fun showEditCurrencyList() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(CONTAINER, EditCurrencySetFragment.newInstance())
            .addToBackStack("EditCurrencyList")
            .commit()
    }


}