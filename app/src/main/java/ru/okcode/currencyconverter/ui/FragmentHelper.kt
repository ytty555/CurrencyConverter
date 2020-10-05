package ru.okcode.currencyconverter.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import ru.okcode.currencyconverter.R

class FragmentHelper {
    companion object {
        fun replace(fragmentActivity: FragmentActivity, fragment: Fragment) {
            val fragmentManager = fragmentActivity.supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}