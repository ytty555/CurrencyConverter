package ru.okcode.currencyconverter.ui.basechooser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.databinding.FragmentBaseChooserBinding

@AndroidEntryPoint
class BaseChooserFragment : Fragment() {
    private lateinit var binding: FragmentBaseChooserBinding

    private val viewModel: BaseChooserViewModel by viewModels()

    private val args: BaseChooserFragmentArgs by navArgs()

    companion object {
        private const val DEFAULT_CURRENCY_AMOUNT: Float = 1.0f
        const val CURRENCY_CODE = "currencyCode"
        const val CURRENCY_AMOUNT = "currencyAmount"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_base_chooser, container, false)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.clickListener = BaseChooserListener { currencyCode, amount ->
            viewModel.updateBase(currencyCode, amount)
            val action =
                BaseChooserFragmentDirections.actionBaseChooserFragmentToCurrencyRatesFragment()
            findNavController().navigate(action)
        }

        viewModel.setCurrencyCode(args.baseCurrencyCode)

        return binding.root
    }
}

class BaseChooserListener(val listener: (currencyCode: String, amount: Float?) -> Unit) {
    fun onOkClick(currencyCode: String, amount: Float?) = listener(currencyCode, amount)
}