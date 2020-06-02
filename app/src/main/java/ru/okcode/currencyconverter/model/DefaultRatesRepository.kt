package ru.okcode.currencyconverter.model

import retrofit2.Call
import ru.okcode.currencyconverter.App
import ru.okcode.currencyconverter.model.api.ApiService
import ru.okcode.currencyconverter.model.api.RatesData
import ru.okcode.currencyconverter.model.db.*
import timber.log.Timber

class DefaultRatesRepository : RatesRepository {

    val api = ApiService.create()
    private val db = RatesDatabase.getInstance(App.getAppContext()).ratesDao


    override suspend fun getRates(forceUpdate: Boolean): RatesData? {
        Timber.tag("ytty").d("forceUpdate = $forceUpdate")
        if (forceUpdate) {
            val remoteData = getRemoteRates()
            Timber.tag("ytty").d("remoteData.date = ${remoteData?.date}")
            Timber.tag("ytty").d("remoteData.baseCurrency = ${remoteData?.baseCurrency}")
            Timber.tag("ytty").d("remoteData.getRatesList() = ${remoteData?.getRatesList()}")
            remoteData?.let {
                saveRatesLocal(it)
            }
        }
        return getLocalRates()
    }

    private fun getRemoteRates(): RatesData? {
        val call: Call<RatesData> = api.getAllLatest()
        var result: RatesData? = null

        val response = call.execute()
        if (response.isSuccessful && response.code() == 200) {
            result = response.body()
        }

        return result
    }

    private suspend fun saveRatesLocal(ratesForSave: RatesData) {
        Timber.tag("ytty").d("saveRatesLocal")
        val operation = OperationEntity(ratesDate = ratesForSave.date)
        val ratesList: List<RateEntity> = ratesForSave.getRatesList().map {
            RateEntity(
                currencyCode = it.currencyCode,
                rateToBase = it.rateToBase,
                rateToEuro = it.rateToEuro
            )
        }
        db.safeRates(operation, ratesList)
    }

    private suspend fun getLocalRates(): RatesData? {
        val ratesList = db.getRates()
        ratesList?.let {
            return RatesDataAdaptor.convertToRatesData(it)
        }
        return null
    }

}