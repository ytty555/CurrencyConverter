package ru.okcode.currencyconverter.ui.basechooser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.mvibase.MviView

private const val ARG_CURRENCY_CODE = "arg_currency_code"
private const val ARG_CURRENCY_AMOUNT = "arg_currency_amount"

@AndroidEntryPoint
class BaseChooserFragment : Fragment(), MviView<BaseChooserIntent, BaseChooserViewState> {

    private val viewModel: BaseChooserViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance(currencyCode: String, currencyAmount: Float): BaseChooserFragment {
            return BaseChooserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CURRENCY_CODE, currencyCode)
                    putFloat(ARG_CURRENCY_AMOUNT, currencyAmount)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_base_chooser, container, false)
    }

    override fun intents(): Observable<BaseChooserIntent> {
        TODO("Not yet implemented")
    }

    override fun render(state: BaseChooserViewState) {
        TODO("Not yet implemented")
    }
}