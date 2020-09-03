package ru.okcode.currencyconverter.model.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.okcode.currencyconverter.model.Config
import ru.okcode.currencyconverter.model.Rates
import ru.okcode.currencyconverter.model.db.ready.ReadyDao
import ru.okcode.currencyconverter.model.readyRates.BaseCurrencyCodeChanger
import ru.okcode.currencyconverter.model.readyRates.RatesDecorator
import ru.okcode.currencyconverter.model.readyRates.ReadyRates
import ru.okcode.currencyconverter.model.readyRates.ReadyRatesController
import javax.inject.Inject

class ReadyRepositoryImpl @Inject constructor(
    readyDao: ReadyDao
): ReadyRepository {

    override val readyRatesDataSource: LiveData<Rates> =
        Transformations.map(readyDao.getReadyRates()) {
            it.toModel()
        }

    override fun updateReadyRates(rates: Rates, config: Config) {
        val readyRatesController: ReadyRates = ReadyRatesController()
        val decorator: RatesDecorator =
            BaseCurrencyCodeChanger(readyRatesController, config.baseCurrencyCode)
        decorator.writeRates(rates)
    }
}