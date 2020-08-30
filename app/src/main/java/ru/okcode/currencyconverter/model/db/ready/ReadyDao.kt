package ru.okcode.currencyconverter.model.db.ready

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReadyDao {
    @Transaction
    @Query("SELECT * FROM ReadyHeader")
    fun getReadyRates(): LiveData<ReadyHeaderWithRates>

    @Transaction
    fun insertToReadyRates(readyHeader: ReadyHeader, ratesList: List<ReadyRate>) {
        clearReadyHeader()
        clearReadyRates()
        insertReadyHeader(readyHeader)
        insertReadyRates(ratesList)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReadyHeader(readyHeader: ReadyHeader)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReadyRates(ratesList: List<ReadyRate>)

    @Query("DELETE FROM ReadyHeader")
    fun clearReadyHeader()

    @Query("DELETE FROM ReadyRate")
    fun clearReadyRates()
}