package ru.okcode.currencyconverter.ui.basechooser

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import ru.okcode.currencyconverter.mvibase.MviViewModel

class BaseChooserViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(), MviViewModel<BaseChooserIntent, BaseChooserViewState> {

    override fun processIntents(intents: Observable<BaseChooserIntent>) {
        TODO("Not yet implemented")
    }

    override fun states(): Observable<BaseChooserViewState> {
        TODO("Not yet implemented")
    }
}