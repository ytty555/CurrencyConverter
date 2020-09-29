package ru.okcode.currencyconverter.ui.basechooser

import android.icu.util.Currency
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.*
import ru.okcode.currencyconverter.data.repository.ConfigRepository
import ru.okcode.currencyconverter.util.getFlagRes

class BaseChooserViewModel @ViewModelInject constructor(
    private val configRepository: ConfigRepository,
    private val textProcessor: TextProcessor,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

}