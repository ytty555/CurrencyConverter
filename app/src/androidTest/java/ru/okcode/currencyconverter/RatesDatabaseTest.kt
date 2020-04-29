package ru.okcode.currencyconverter

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.okcode.currencyconverter.model.db.*
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RatesDatabaseTest {

    private lateinit var ratesDao: RatesDao
    private lateinit var db: RatesDatabase
    private lateinit var currencyList: List<CurrencyEntity>

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, RatesDatabase::class.java
        ).build()
        ratesDao = db.ratesDao
    }

    @Before
    fun prepopulateCurrency() {
        val currencyRUB = CurrencyEntity("RUB", R.drawable.ic_rub, R.string.RUB)
        val currencyEUR = CurrencyEntity("EUR", R.drawable.ic_eur, R.string.EUR)
        val currencyUSD = CurrencyEntity("USD", R.drawable.ic_usd, R.string.USD)
        currencyList = listOf(currencyRUB, currencyEUR, currencyUSD)
        ratesDao.prepopulateAllCurrencies(currencyList)
    }


    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun couldNotGetRateWhenNoRateInserted() {
        val ratesList: List<CurrencyRate> = ratesDao.getAllRates()
        assertTrue(ratesList.isEmpty())
    }





}