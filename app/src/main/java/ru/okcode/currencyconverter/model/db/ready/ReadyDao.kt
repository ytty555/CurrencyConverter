package ru.okcode.currencyconverter.model.db.ready

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReadyDao {
    @Transaction
    @Query("SELECT * FROM ReadyHeader")
    fun getReadyRates(): LiveData<ReadyHeaderWithRates?>

    @Transaction
    suspend fun insertToReadyRates(readyHeaderWithRates: ReadyHeaderWithRates) {
        clearReadyHeader()
        clearReadyRates()
        insertReadyHeader(readyHeaderWithRates.readyHeader)
        insertReadyRates(readyHeaderWithRates.rates)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReadyHeader(readyHeader: ReadyHeader)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReadyRates(ratesList: List<ReadyRate>)

    @Query("DELETE FROM ReadyHeader")
    suspend fun clearReadyHeader()

    @Query("DELETE FROM ReadyRate")
    suspend fun clearReadyRates()
}