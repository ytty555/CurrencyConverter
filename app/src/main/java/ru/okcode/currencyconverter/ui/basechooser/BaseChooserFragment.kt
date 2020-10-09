package ru.okcode.currencyconverter.ui.basechooser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.mvibase.MviView
import timber.log.Timber

private const val ARG_CURRENCY_CODE = "arg_currency_code"
private const val ARG_CURRENCY_AMOUNT = "arg_currency_amount"

@AndroidEntryPoint
class BaseChooserFragment : Fragment(), MviView<BaseChooserIntent, BaseChooserViewState> {

    private val viewModel: BaseChooserViewModel by viewModels()
    private val disposables = CompositeDisposable()

    private val pressDigitPublisher =
        PublishSubject.create<BaseChooserIntent.PressDigitIntent>()

    private val pressAdditionalPublisher =
        PublishSubject.create<BaseChooserIntent.PressAdditionalIntent>()

    private val pressCalculationPublisher =
        PublishSubject.create<BaseChooserIntent.PressCalculationIntent>()

    private val cancelPublisher =
        PublishSubject.create<BaseChooserIntent.CancelIntent>()

    private lateinit var currencyNameTextView: TextView
    private lateinit var currencyCodeTextView: TextView
    private lateinit var currencySymbolTextView: TextView
    private lateinit var currencyFlagImageView: ImageView
    private lateinit var displayValueTextView: TextView

    private lateinit var operand0: Button
    private lateinit var operand1: Button
    private lateinit var operand2: Button
    private lateinit var operand3: Button
    private lateinit var operand4: Button
    private lateinit var operand5: Button
    private lateinit var operand6: Button
    private lateinit var operand7: Button
    private lateinit var operand8: Button
    private lateinit var operand9: Button
    private lateinit var operandComma: Button
    private lateinit var operandErase: Button

    private lateinit var calc1: Button
    private lateinit var calc10: Button
    private lateinit var calc100: Button
    private lateinit var calc1000: Button
    private lateinit var calcOverall: Button

    private lateinit var baseCurrencyCode: String
    private var startBaseCurrencyAmount: Float? = null

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
        val view = inflater.inflate(R.layout.fragment_base_chooser, container, false)

        baseCurrencyCode = requireArguments().getString(ARG_CURRENCY_CODE)!!
        startBaseCurrencyAmount = requireArguments().getFloat(ARG_CURRENCY_AMOUNT)

        controlsInit(view)

        setButtonsOnClickListeners()

        viewModel.processIntents(intents())

        return view
    }

    private fun controlsInit(view: View) {
        currencyCodeTextView = view.findViewById(R.id.currency_code)
        currencyFlagImageView = view.findViewById(R.id.currency_flag)
        currencySymbolTextView = view.findViewById(R.id.currency_symbol)
        currencyNameTextView = view.findViewById(R.id.currency_name)
        displayValueTextView = view.findViewById(R.id.display)

        operand0 = view.findViewById(R.id.b_0)
        operand1 = view.findViewById(R.id.b_01)
        operand2 = view.findViewById(R.id.b_02)
        operand3 = view.findViewById(R.id.b_03)
        operand4 = view.findViewById(R.id.b_04)
        operand5 = view.findViewById(R.id.b_05)
        operand6 = view.findViewById(R.id.b_06)
        operand7 = view.findViewById(R.id.b_07)
        operand8 = view.findViewById(R.id.b_08)
        operand9 = view.findViewById(R.id.b_09)
        operandComma = view.findViewById(R.id.b_comma)
        operandErase = view.findViewById(R.id.b_erase)

        calc1 = view.findViewById(R.id.b_ok_1)
        calc10 = view.findViewById(R.id.b_ok_10)
        calc100 = view.findViewById(R.id.b_ok_100)
        calc1000 = view.findViewById(R.id.b_ok_1000)
        calcOverall = view.findViewById(R.id.b_ok_overall)
    }

    private fun setButtonsOnClickListeners() {
        operand0.setOnClickListener {
            pressDigitPublisher.onNext(
                BaseChooserIntent.PressDigitIntent(
                    DigitOperand.OPERAND_0
                )
            )
        }

        operand1.setOnClickListener {
            Timber.d("setOnClickListener 1")
            pressDigitPublisher.onNext(
                BaseChooserIntent.PressDigitIntent(
                    DigitOperand.OPERAND_1
                )
            )

        }

        operand2.setOnClickListener {
            pressDigitPublisher.onNext(
                BaseChooserIntent.PressDigitIntent(
                    DigitOperand.OPERAND_2
                )
            )
        }

        operand3.setOnClickListener {
            pressDigitPublisher.onNext(
                BaseChooserIntent.PressDigitIntent(
                    DigitOperand.OPERAND_3
                )
            )
        }

        operand4.setOnClickListener {
            pressDigitPublisher.onNext(
                BaseChooserIntent.PressDigitIntent(
                    DigitOperand.OPERAND_4
                )
            )
        }

        operand5.setOnClickListener {
            pressDigitPublisher.onNext(
                BaseChooserIntent.PressDigitIntent(
                    DigitOperand.OPERAND_5
                )
            )
        }

        operand6.setOnClickListener {
            pressDigitPublisher.onNext(
                BaseChooserIntent.PressDigitIntent(
                    DigitOperand.OPERAND_6
                )
            )
        }

        operand7.setOnClickListener {
            pressDigitPublisher.onNext(
                BaseChooserIntent.PressDigitIntent(
                    DigitOperand.OPERAND_7
                )
            )
        }

        operand8.setOnClickListener {
            pressDigitPublisher.onNext(
                BaseChooserIntent.PressDigitIntent(
                    DigitOperand.OPERAND_8
                )
            )
        }

        operand9.setOnClickListener {
            pressDigitPublisher.onNext(
                BaseChooserIntent.PressDigitIntent(
                    DigitOperand.OPERAND_9
                )
            )
        }

        operandComma.setOnClickListener {
            pressAdditionalPublisher.onNext(
                BaseChooserIntent.PressAdditionalIntent(AdditionalOperand.Comma)
            )
        }

        operandErase.setOnClickListener {
            pressAdditionalPublisher.onNext(
                BaseChooserIntent.PressAdditionalIntent(AdditionalOperand.Erase)
            )
        }

        calc1.setOnClickListener {
            pressCalculationPublisher.onNext(
                BaseChooserIntent.PressCalculationIntent(
                    currencyCode = baseCurrencyCode,
                    calculation = Calculation.RESULT_1
                )
            )
        }

        calc10.setOnClickListener {
            pressCalculationPublisher.onNext(
                BaseChooserIntent.PressCalculationIntent(
                    currencyCode = baseCurrencyCode,
                    calculation = Calculation.RESULT_10
                )
            )
        }

        calc100.setOnClickListener {
            pressCalculationPublisher.onNext(
                BaseChooserIntent.PressCalculationIntent(
                    currencyCode = baseCurrencyCode,
                    calculation = Calculation.RESULT_100
                )
            )
        }

        calc1000.setOnClickListener {
            pressCalculationPublisher.onNext(
                BaseChooserIntent.PressCalculationIntent(
                    currencyCode = baseCurrencyCode,
                    calculation = Calculation.RESULT_1000
                )
            )
        }

        calcOverall.setOnClickListener {
            pressCalculationPublisher.onNext(
                BaseChooserIntent.PressCalculationIntent(
                    currencyCode = baseCurrencyCode,
                    calculation = Calculation.RESULT_OVERALL
                )
            )
        }
    }

    override fun onStart() {
        Timber.d("onStart()")
        super.onStart()
        bind()
    }

    private fun bind() {
        disposables.add(viewModel.states().subscribe(this::render))
    }

    override fun onStop() {
        Timber.d("onStop()")
        super.onStop()
        disposables.clear()
    }

    override fun intents(): Observable<BaseChooserIntent> {
        return Observable.merge(
            loadCurrencyInfoIntent(),
            pressDigitIntent(),
            pressAdditionalIntent(),
            pressCalculationIntent()
        )
            .mergeWith(
                cancelIntent()
            )
    }

    private fun loadCurrencyInfoIntent(): Observable<BaseChooserIntent.LoadCurrencyInfoIntent> {
        return Observable.just(
            BaseChooserIntent.LoadCurrencyInfoIntent(
                baseCurrencyCode,
                startBaseCurrencyAmount
            )
        )
    }

    private fun pressDigitIntent(): Observable<BaseChooserIntent.PressDigitIntent> {
        return pressDigitPublisher
            .doOnNext{
                Timber.d("pressDigitIntent() with ${it.digitOperand.value}" )
            }
    }

    private fun pressAdditionalIntent(): Observable<BaseChooserIntent.PressAdditionalIntent> {
        return pressAdditionalPublisher
            .doOnNext{
                Timber.d("pressAdditionalIntent() with ${it.additionalOperand}" )
            }
    }

    private fun pressCalculationIntent(): Observable<BaseChooserIntent.PressCalculationIntent> {
        return pressCalculationPublisher
            .doOnNext{
                Timber.d("pressCalculationIntent() with ${it.calculation}" )
            }
    }

    private fun cancelIntent(): Observable<BaseChooserIntent.CancelIntent> {
        return cancelPublisher
    }

    override fun render(state: BaseChooserViewState) {
        Timber.d("render state $state")
        startBaseCurrencyAmount = null

        state.currency?.let { currency ->
            currencyCodeTextView.text = currency.currencyCode
            currencySymbolTextView.text = currency.symbol
            currencyNameTextView.text = currency.displayName
        }

        state.flagRes?.let { flagRes ->
            currencyFlagImageView.setImageResource(flagRes)
        }

        displayValueTextView.text = state.displayValue

        state.error?.let {
            Timber.d("Render on state.error $it")
            it.localizedMessage?.let { message ->
                showMessage(message)
            }
        }

    }

    private fun showMessage(message: String) {
        Toast.makeText(
            activity,
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}