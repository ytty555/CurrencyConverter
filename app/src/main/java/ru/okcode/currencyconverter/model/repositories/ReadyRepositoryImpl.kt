package ru.okcode.currencyconverter.model.repositories

import androidx.lifecycle.LiveData
import ru.okcode.currencyconverter.model.Config
import ru.okcode.currencyconverter.model.Rates
import ru.okcode.currencyconverter.model.readyRates.BaseCurrencyCodeChanger
import ru.okcode.currencyconverter.model.readyRates.RatesDecorator
import ru.okcode.currencyconverter.model.readyRates.ReadyRates
import ru.okcode.currencyconverter.model.readyRates.ReadyRatesController

class ReadyRepositoryImpl: ReadyRepository {
    override val readyRatesDataSource: LiveData<Rates>
        get() = TODO("Not yet implemented")

    override fun updateReadyRates(rates: Rates, config: Config) {
        val readyRatesController: ReadyRates = ReadyRatesController()
        val decorator: RatesDecorator =
            BaseCurrencyCodeChanger(readyRatesController, config.baseCurrencyCode)
        decorator.writeRates(rates)
    }
}