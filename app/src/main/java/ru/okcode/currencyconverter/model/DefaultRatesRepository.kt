package ru.okcode.currencyconverter.model

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.okcode.currencyconverter.model.api.ApiService
import ru.okcode.currencyconverter.model.db.*

class DefaultRatesRepository(
    private val ratesRemoteDataSource: ApiService,
    private val ratesLocalDataSource: RatesDao
) : RatesRepository {
    private val TAG = "DefaultRatesRepository"
    private lateinit var currenciesList: List<CurrencyEntity>

    override suspend fun getRates(forceUpdate: Boolean): Result<RatesData> {
        if (forceUpdate) {
            try {
                val ratesDataResult: Result<RatesData> = ratesRemoteDataSource.getRates()
                if (ratesDataResult is Result.Success) {
                    saveRatesLocal(ratesDataResult.data)
                } else {
                    TODO("Вывести ошибку в snackbar")
                }
            } catch (ex: Exception) {
                return Result.Error(ex)
            }
        }
        return getRatesLocal()
    }

    private suspend fun saveRatesLocal(ratesForSave: RatesData) {
        val operation = OperationEntity(ratesDate = ratesForSave.date)
        val rates = ratesForSave.rates.map { rate ->
            RateEntity(
                currencyCode = rate.currencyCode,
                rateToEuro = rate.rateToEuro
            )
        }
        ratesLocalDataSource.safeRates(operation, rates)
    }

    private suspend fun getRatesLocal(): Result<RatesData> {
        val dbData = ratesLocalDataSource.getRates()
            ?: return Result.Error(Exception("Can't load rates from the local database"))

        return Result.Success(RatesDataAdaptor.convertToRatesData(dbData))
    }

}