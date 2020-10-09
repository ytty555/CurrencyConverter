package ru.okcode.currencyconverter.ui.editcurrencyset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.okcode.currencyconverter.R

class EditCurrencySetFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_currency_set, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            EditCurrencySetFragment()

    }
}