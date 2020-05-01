package ru.okcode.currencyconverter.model.db

import androidx.room.*

@Dao
@TypeConverters(Converters::class)
interface RatesDao {

    @Transaction
    suspend fun safeRates(operation: OperationEntity, rates: List<RateEntity>) {
        // Clear old data
        clearOperation()

        // Insetr new data
        val operationId = insertOperation(operation)
        for (rate in rates) {
            rate.hostOperationId = operationId
        }
        insertRates(rates)
    }

    @Query("SELECT * FROM operation_table")
    fun getRates(): CurrencyRatesList?

    @Query("SELECT * FROM currency_table ORDER BY currency_code")
    fun getCurrenciesList(): List<CurrencyEntity>

    @Query("DELETE FROM operation_table")
    fun clearOperation()

    @Insert
    fun insertOperation(operation: OperationEntity): Long

    @Insert
    fun insertRates(ratesList: List<RateEntity>)

    @Insert
    fun prepopulateCurrencies(currenciesList: List<CurrencyEntity>)
}