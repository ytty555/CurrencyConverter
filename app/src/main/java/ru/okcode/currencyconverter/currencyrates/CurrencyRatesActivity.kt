package ru.okcode.currencyconverter.currencyrates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.databinding.ActivityCurrencyRatesBinding

class CurrencyRatesActivity : AppCompatActivity() {

    private val TAG: String = "CurrencyRatesActivity"

    private lateinit var binding: ActivityCurrencyRatesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_currency_rates
        )

    }

}
