package ru.okcode.currencyconverter.model.db.ready

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReadyDao {
    @Transaction
    @Query("SELECT * FROM ReadyHeader")
    suspend fun getReadyRates(): LiveData<ReadyHeaderWithRates>

    @Transaction
    suspend fun insertToReadyRates(readyHeader: ReadyHeader, ratesList: List<ReadyRate>) {
        clearReadyHeader()
        clearReadyRates()
        insertReadyHeader(readyHeader)
        insertReadyRates(ratesList)
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