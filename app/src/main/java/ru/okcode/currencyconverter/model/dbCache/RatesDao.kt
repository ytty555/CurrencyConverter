package ru.okcode.currencyconverter.model.dbCache

import androidx.room.*
import org.jetbrains.annotations.TestOnly

@Dao
@TypeConverters(Converters::class)
interface RatesDao {

    @Transaction
    fun safeRates(operation: OperationEntity, rates: List<RateEntity>) {
        // Clear old data
        clearOperation()

        // Insetr new data
        val operationId = insertOperation(operation)
        for (rate in rates) {
            rate.hostOperationId = operationId
        }
        insertRates(rates)
    }

    @Transaction
    @Query("SELECT * FROM operation_table")
    fun getRates(): CurrencyRatesList?


    @Query("DELETE FROM operation_table")
    fun clearOperation()

    @Insert
    fun insertOperation(operation: OperationEntity): Long

    @Insert
    fun insertRates(ratesList: List<RateEntity>)

    @Query("SELECT * FROM rate_table")
    @TestOnly
    fun getOnlyRates(): List<RateEntity>
}