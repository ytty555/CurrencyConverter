package ru.okcode.currencyconverter.model.db

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface RatesDao {

    @Insert
    fun insert(rate: CurrencyRate)

    @Update
    fun update(rate: CurrencyRate)

    @Query("SELECT * FROM currency_rates_table ORDER BY currencyId")
    fun getAllRates(): LiveData<List<CurrencyRate>>

    @Query("SELECT * FROM currency_rates_table WHERE currencyId = :currencyId")
    fun getById(currencyId: String): CurrencyRate?
}