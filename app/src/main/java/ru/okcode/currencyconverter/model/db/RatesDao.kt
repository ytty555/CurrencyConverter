package ru.okcode.currencyconverter.model.db

import androidx.room.*

@Dao
interface RatesDao {

   @Insert
   fun insertRate(rate: RateEntity)

   @Insert
   fun prepopulateAllCurrencies(currenciesList: List<CurrencyEntity>)

   @Transaction
   @Query("SELECT * FROM currency_table  where currency_code = :currency")
   fun getRateByCurrency(currency: String): CurrencyRate

   @Transaction
   @Query("SELECT * FROM currency_table ORDER BY currency_code")
   fun getAllRates(): List<CurrencyRate>


}