package ru.okcode.currencyconverter.ui

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.ui.basechooser.BaseChooserFragment
import ru.okcode.currencyconverter.ui.editcurrencyset.EditCurrencySetFragment
import ru.okcode.currencyconverter.ui.overview.OverviewFragment
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {
    var activity: FragmentActivity? = null

    companion object {
        private const val CONTAINER = R.id.fragment_container
        private const val BACK_STACK_ROOT_TAG = "root_fragment"
    }

    fun showOverview() {
        Timber.d("showOverview()")

        val fragmentManager = activity?.supportFragmentManager

        fragmentManager?.let {
            // Pop off everything including the root fragment
            it.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)

            // Add the new root fragment - OverviewFragment
            it.beginTransaction()
                .replace(CONTAINER, OverviewFragment.newInstance())
                .addToBackStack(BACK_STACK_ROOT_TAG)
                .commit()
        }
    }

    fun showBaseChooser(currencyCode: String, currencyAmount: Float) {
        Timber.d("showBaseChooser($currencyCode, $currencyAmount) activity $activity")
        activity?.let {
            it.supportFragmentManager
                .beginTransaction()
                .replace(CONTAINER, BaseChooserFragment.newInstance(currencyCode, currencyAmount))
                .addToBackStack(null)
                .commit()
        }

    }

    fun showEditCurrencyList() {
        Timber.d("showEditCurrencyList(")
        activity?.let {
            it.supportFragmentManager
                .beginTransaction()
                .replace(CONTAINER, EditCurrencySetFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }

}