package ru.okcode.currencyconverter.ui.overview

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.ui.basechooser.BaseChooserActivity
import ru.okcode.currencyconverter.ui.editcurrencylist.EditCurrenciesListActivity
import ru.okcode.currencyconverter.ui.basechooser.BaseChooserFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OverviewNavigator @Inject constructor() {
    var twoPane: Boolean = false
    var activity: FragmentActivity? = null

    fun showBaseChooser(currencyCode: String, currencyAmount: Float) {
        if (twoPane) {
            val fragment =
                BaseChooserFragment.newInstance(currencyCode, currencyAmount)
            activity?.let {
                it.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.base_chooser_container, fragment)
                    .commit()
            }
        } else {
            activity?.let {
                val intent = Intent(it, BaseChooserActivity::class.java)
                intent.putExtra(BaseChooserFragment.ARG_CURRENCY_CODE, currencyCode)
                intent.putExtra(BaseChooserFragment.ARG_CURRENCY_AMOUNT, currencyAmount)
                it.startActivity(intent)
            }
        }

    }

    fun showEditCurrencyList() {
        activity?.let {
            val intent = Intent(it, EditCurrenciesListActivity::class.java)
            it.startActivity(intent)
        }
    }
}