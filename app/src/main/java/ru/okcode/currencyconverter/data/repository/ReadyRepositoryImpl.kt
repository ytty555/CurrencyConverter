package ru.okcode.currencyconverter.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import io.reactivex.Observable
import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.data.db.ready.ReadyDao
import ru.okcode.currencyconverter.data.db.ready.ReadyMapper
import ru.okcode.currencyconverter.data.db.ready.readydecorator.BaseCurrencyCodeChanger
import ru.okcode.currencyconverter.data.db.ready.readydecorator.RatesDecorator
import ru.okcode.currencyconverter.data.db.ready.readydecorator.ReadyRates
import ru.okcode.currencyconverter.data.db.ready.readydecorator.ReadyRatesController
import javax.inject.Inject

class ReadyRepositoryImpl @Inject constructor(
    private val readyDao: ReadyDao,
    private val readyMapper: ReadyMapper
) : ReadyRepository {


//    override suspend fun updateReadyRates(rates: Rates, config: Config) {
//        val readyRatesController: ReadyRates = ReadyRatesController(readyDao, readyMapper)
//        val decorator: RatesDecorator =
//            BaseCurrencyCodeChanger(readyRatesController, config)
//        decorator.writeRates(rates)
//    }

    override fun getAllRates(): Observable<Rates> {
        return readyDao.getReadyRates()
            .map {
                readyMapper.mapToModel(it)
            }
    }
}