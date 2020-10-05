package ru.okcode.currencyconverter.ui.basechooser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.okcode.currencyconverter.R

@AndroidEntryPoint
class BaseChooserFragment : Fragment() {

    private val viewModel: BaseChooserViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_base_chooser, container, false)
    }


    companion object {
        @JvmStatic
        fun newInstance(currencyCode: String, currencyAmount: Float): BaseChooserFragment {
            return BaseChooserFragment().apply {
                arguments = Bundle().apply {
                    putString(BaseChooserViewModel.ARG_CURRENCY_CODE, currencyCode)
                    putFloat(BaseChooserViewModel.ARG_CURRENCY_AMOUNT, currencyAmount)
                }
            }
        }
    }
}