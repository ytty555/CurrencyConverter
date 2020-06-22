package ru.okcode.currencyconverter.model.db

import android.util.Log
import androidx.room.*
import io.reactivex.Observable
import ru.okcode.currencyconverter.model.CommonRates
import ru.okcode.currencyconverter.model.Converters
import ru.okcode.currencyconverter.utils.getRateToBase

@Dao
@TypeConverters(Converters::class)
interface RatesDao {

    fun safeRates(commonRates: CommonRates): Observable<CommonRates> {
        // Clear old data
        clear()

        // Convert data
        val dataSet = DataSetEntity(
            actualDate = commonRates.commonRatesDataSet.actualDate,
            baseCurrencyCode = commonRates.commonRatesDataSet.baseCurrencyCode,
            baseCurrencyAmount = commonRates.commonRatesDataSet.baseCurrencyAmount,
            baseCurrencyRateToEuro = commonRates.commonRatesDataSet.baseCurrencyRateToEuro
        )
        val ratesList: List<RateEntity> = commonRates.commonRatesList.map { commonRateItem ->
            Log.e("qq", "dataSet ID $dataSet.id")
            RateEntity(
                currencyCode = commonRateItem.currencyCode,
                rateToEuro = commonRateItem.rateToEuro,
                rateToBase = getRateToBase(
                    baseCurrencyRateToEuro = commonRates.commonRatesDataSet.baseCurrencyRateToEuro,
                    baseCurrencyAmount = commonRates.commonRatesDataSet.baseCurrencyAmount,
                    currentCurrencyRateToEuro = commonRateItem.rateToEuro
                ),
                hostOperationId = dataSet.id
            )
        }
        Log.e("qq", "ratesList hostOperationId ${ratesList[0].hostOperationId}")


        // Insert data
        insert(dataSet, ratesList)
        return Observable.just(commonRates)
    }

    @Transaction
    fun insert(dataSet: DataSetEntity, ratesList: List<RateEntity>) {
        insertDataSet(dataSet)
        insertRatesList(ratesList)
    }

    @Transaction
    @Query("SELECT * FROM operation_table")
    fun getRates(): Observable<CurrencyRatesList?>


    @Query("DELETE FROM operation_table")
    fun clear()

    @Insert
    fun insertDataSet(dataSet: DataSetEntity)

    @Insert
    fun insertRatesList(ratesList: List<RateEntity>)
}