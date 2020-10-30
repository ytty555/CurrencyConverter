package ru.okcode.currencyconverter.ui.editcurrencylist

import androidx.fragment.app.FragmentActivity
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.data.model.ConfiguredCurrency
import timber.log.Timber
import javax.inject.Inject

class EditNavigator @Inject constructor() {
    var activity: FragmentActivity? = null

    fun showPriorityPositionFragment(currencies: List<ConfiguredCurrency>) {
        showFragment(PriorityPositionFragment())
    }


    fun showAddCurrenciesFragment(currencies: List<ConfiguredCurrency>) {
        TODO("Not implemented yet")
    }

    private fun showFragment(fragment: PriorityPositionFragment) {
        activity?.let {
            val fragmentManager = it.supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }
}