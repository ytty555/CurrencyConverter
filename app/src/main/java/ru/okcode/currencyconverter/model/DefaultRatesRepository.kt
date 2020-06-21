package ru.okcode.currencyconverter.model

import io.reactivex.Observable
import ru.okcode.currencyconverter.App
import ru.okcode.currencyconverter.model.api.ApiService
import ru.okcode.currencyconverter.model.api.RatesApiData
import ru.okcode.currencyconverter.model.db.DbCache
import ru.okcode.currencyconverter.model.db.DbWork
import ru.okcode.currencyconverter.model.props.AppPreferencies
import ru.okcode.currencyconverter.utils.convertToCommonRates
import ru.okcode.currencyconverter.utils.updateBase

class DefaultRatesRepository : RatesRepository {

    private val api = ApiService.create()
    private val dbCache = DbCache.getInstance(App.getAppContext()).cacheRatesDao
    private val dbWork = DbWork.getInstance(App.getAppContext()).workRatesDao
    private val prefs = AppPreferencies(App.getAppContext())

    /**
     * Gets raw rates data from cache or remote api
     * If @param forceUpdate is true, then function request data form api first.
     * Else data come form cache only
     */
    override fun getRatesDataSource(forceUpdate: Boolean): Observable<CommonRates> {
        if (forceUpdate) {
            // Request data form REST API and save it to the cache
            return apiData()
                // конвертирует и записывает данные из api в кэш
                .concatMap { ratesApiData: RatesApiData ->
                    val commonRatesFromApi = convertToCommonRates(ratesApiData)
                    saveToCache(commonRatesFromApi)
                }
                // Записывает данные в базу данных Work
                .concatMap { commonRates: CommonRates ->
                    val baseCurrencyCode = prefs.prefsBaseCurrencyCode
                    val baseCurrencyAmount = prefs.prefsBaseCurrencyAmount
                    val newCommonRates = updateBase(
                        commonRates = commonRates,
                        baseCurrencyCode = baseCurrencyCode,
                        baseCurrencyAmount = baseCurrencyAmount
                    )
                    saveToWorkDb(newCommonRates)
                }
        } else {
            // Get data from the cache
            return getFromWorkDb()
        }
    }

    private fun apiData() = api.getAllLatest()

    private fun saveToCache(commonRates: CommonRates): Observable<CommonRates> {
        return dbCache.safeRates(commonRates)
    }

    private fun saveToWorkDb(commonRates: CommonRates): Observable<CommonRates> {
        return dbWork.safeRates(commonRates)
    }

    private fun getFromWorkDb(): Observable<CommonRates> {
        return dbWork.getRates()
            .flatMap {
                Observable.just(convertToCommonRates(it))
            }
    }

}