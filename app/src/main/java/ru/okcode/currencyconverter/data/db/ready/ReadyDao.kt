package ru.okcode.currencyconverter.data.db.ready

import androidx.room.*
import io.reactivex.Maybe

@Dao
interface ReadyDao {
    @Transaction
    @Query("SELECT * FROM ReadyHeader")
    fun getReadyRates(): Maybe<ReadyHeaderWithRates>

    @Transaction
    fun insertToReadyRates(readyHeaderWithRates: ReadyHeaderWithRates) {
        clearReadyHeader()
        clearReadyRates()
        insertReadyHeader(readyHeaderWithRates.readyHeader)
        insertReadyRates(readyHeaderWithRates.rates)
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