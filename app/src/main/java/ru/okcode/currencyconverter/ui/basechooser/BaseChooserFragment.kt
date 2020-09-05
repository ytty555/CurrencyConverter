package ru.okcode.currencyconverter.ui.basechooser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.databinding.FragmentBaseChooserBinding

@AndroidEntryPoint
class BaseChooserFragment: Fragment() {
    private lateinit var binding: FragmentBaseChooserBinding

    private val viewModel: BaseChooserViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_base_chooser, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }
}