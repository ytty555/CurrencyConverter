package ru.okcode.currencyconverter.data.db.ready

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ReadyDao{

    @Query("SELECT * FROM ReadyHeader")
    fun getReadyRatesSingle(): Single<ReadyHeaderWithRates>

    @Query("SELECT * FROM ReadyHeader")
    fun getReadyRatesFlowable(): Flowable<ReadyHeaderWithRates>

    @Transaction
    fun insert(readyHeaderWithRates: ReadyHeaderWithRates) {
        clearHeader()
        clearRates()
        insertHeader(readyHeaderWithRates.readyHeader)
        insertRates(readyHeaderWithRates.rates)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHeader(header: ReadyHeader)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRates(rates: List<ReadyRate>)

    @Query("DELETE FROM ReadyHeader")
    fun clearHeader()

    @Query("DELETE FROM ReadyRate")
    fun clearRates()
}

