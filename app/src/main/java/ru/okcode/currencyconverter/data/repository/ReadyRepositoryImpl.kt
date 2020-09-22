package ru.okcode.currencyconverter.data.repository

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import ru.okcode.currencyconverter.data.db.ready.ReadyDao
import ru.okcode.currencyconverter.data.db.ready.ReadyMapper
import ru.okcode.currencyconverter.data.model.Rates
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
          return Observable.create { ratesEmitter ->
              readyDao.getReadyRates()
                  .subscribeBy(
                      onSuccess = {
                          ratesEmitter.onNext(readyMapper.mapToModel(it)?: Rates.idle())
                          ratesEmitter.onComplete()
                      },
                      onComplete = {
                          ratesEmitter.onNext(Rates.idle())
                          ratesEmitter.onComplete()
                      },
                      onError = {
                          ratesEmitter.onError(it)
                      }
                  )
          }



    }
}