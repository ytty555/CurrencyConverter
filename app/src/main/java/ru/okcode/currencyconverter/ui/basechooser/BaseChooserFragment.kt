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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_base_chooser, container, false)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        viewModel.setCurrencyCode(args.baseCurrencyCode)

        viewModel.closeBaseChooser.observe(viewLifecycleOwner, { closeBaseChooser ->
            if (closeBaseChooser) {
                val action =
                    BaseChooserFragmentDirections.actionBaseChooserFragmentToCurrencyRatesFragment()
                findNavController().navigate(action)
            }
        })

        return binding.root
    }
}