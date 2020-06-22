package ru.okcode.currencyconverter.model.db

import android.util.Log
import androidx.room.*
import io.reactivex.Observable
import ru.okcode.currencyconverter.model.CommonRates
import ru.okcode.currencyconverter.model.Converters
import ru.okcode.currencyconverter.utils.getRateToBase
import kotlin.random.Random

@Dao
@TypeConverters(Converters::class)
interface RatesDao {

    fun safeRates(commonRates: CommonRates): Observable<CommonRates> {
        // Clear old data
        clear()

        // Convert data
        val foreignKey: Long = Random.nextLong(Long.MAX_VALUE)

        val dataSet = DataSetEntity(
            id = foreignKey,
            actualDate = commonRates.commonRatesDataSet.actualDate,
            baseCurrencyCode = commonRates.commonRatesDataSet.baseCurrencyCode,
            baseCurrencyAmount = commonRates.commonRatesDataSet.baseCurrencyAmount,
            baseCurrencyRateToEuro = commonRates.commonRatesDataSet.baseCurrencyRateToEuro
        )

        val ratesList: List<RateEntity> = commonRates.commonRatesList.map { commonRateItem ->
            RateEntity(
                currencyCode = commonRateItem.currencyCode,
                rateToEuro = commonRateItem.rateToEuro,
                rateToBase = getRateToBase(
                    baseCurrencyRateToEuro = commonRates.commonRatesDataSet.baseCurrencyRateToEuro,
                    baseCurrencyAmount = commonRates.commonRatesDataSet.baseCurrencyAmount,
                    currentCurrencyRateToEuro = commonRateItem.rateToEuro
                ),
                hostOperationId = foreignKey
            )
        }

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