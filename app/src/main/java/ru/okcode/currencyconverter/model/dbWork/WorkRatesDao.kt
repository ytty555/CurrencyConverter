package ru.okcode.currencyconverter.model.dbWork

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface WorkRatesDao {

    @Transaction
    @Query("SELECT * FROM work_set_table")
    fun getRates(): Observable<WorkRatesList>

    @Query("DELETE FROM work_set_table")
    fun clear(): Completable

    @Insert
    fun insertWorkSet(workSet: WorkRatesSet): Long

    @Insert
    fun insertWorkRatesList(workRatesList: List<WorkRatesItem>)

    @Transaction
    fun insert(workSet: WorkRatesSet, rates: List<WorkRatesItem>) {
        clear()

        val workSetId = insertWorkSet(workSet)
        for (rate in rates) {
            rate.hostRatesSetId = workSetId
        }
    }

}