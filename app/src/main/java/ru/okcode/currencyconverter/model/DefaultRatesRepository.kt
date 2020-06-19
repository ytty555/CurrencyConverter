package ru.okcode.currencyconverter.model

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ru.okcode.currencyconverter.App
import ru.okcode.currencyconverter.model.api.ApiService
import ru.okcode.currencyconverter.model.api.RatesData
import ru.okcode.currencyconverter.model.dbCache.OperationEntity
import ru.okcode.currencyconverter.model.dbCache.RateEntity
import ru.okcode.currencyconverter.model.dbCache.RatesDatabase

class DefaultRatesRepository : RatesRepository {

    private val api = ApiService.create()
    private val db = RatesDatabase.getInstance(App.getAppContext()).ratesDao
    private val apiDataSource: Observable<RatesData> = api.getAllLatest()

    /**
     * Gets raw rates data from cache or remote api
     * If @param forceUpdate is true, then function request data form api first.
     * Else data come form cache only
     */
    override fun getRatesDataSource(forceUpdate: Boolean): Observable<RatesData> {
        if (forceUpdate) {
            // Request data form remote API and save it to the cache
            return Observable.create { s ->
                apiDataSource
                    .subscribeOn(Schedulers.io())
                    .subscribe({ratesData ->
                        saveToCache(ratesData).subscribe({},{
                            it.printStackTrace()
                        })
                        s.onNext(ratesData)
                        s.onComplete()
                    }, {
                        val e: Throwable = Exception("Could not get data from remote API")
                        s.onError(e)
                    })
            }
        } else {
            // Get data from the cache
            return Observable.create {
                val data = getFromCache()
                if (data != null) {
                    it.onNext(data)
                    it.onComplete()
                } else {
                    val e: Throwable = Exception("Cache is empty")
                    it.onError(e)
                }
            }
        }
    }


    private fun saveToCache(ratesForSave: RatesData): Completable {
        return Completable.create {emitter ->
            try {
                val operation = OperationEntity(ratesDate = ratesForSave.date)
                val ratesList: List<RateEntity> = ratesForSave.getRatesList().map {
                    RateEntity(
                        currencyCode = it.currencyCode,
                        rateToBase = it.rateToBase,
                        rateToEuro = it.rateToEuro
                    )
                }
                db.safeRates(operation, ratesList)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }

        }
    }

    private fun getFromCache(): RatesData? {
        val ratesList = db.getRates()
        ratesList?.let {
            return RatesDataAdaptor.convertToRatesData(it)
        }
        return null
    }

}