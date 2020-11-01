package ru.okcode.currencyconverter.ui.editcurrencylist

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import ru.okcode.currencyconverter.R
import javax.inject.Inject

class EditNavigator @Inject constructor() {
    var activity: FragmentActivity? = null

    fun showPriorityPositionFragment() {
        showFragment(PriorityPositionFragment())
    }


    fun showAddCurrenciesFragment() {
        showFragment(AddCurrenciesFragment())
    }

    private fun showFragment(fragment: Fragment) {
        activity?.let {
            val fragmentManager = it.supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }
}