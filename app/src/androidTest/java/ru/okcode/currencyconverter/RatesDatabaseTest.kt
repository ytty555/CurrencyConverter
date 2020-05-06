package ru.okcode.currencyconverter

import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.okcode.currencyconverter.model.db.*
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class RatesDatabaseTest {

    private val scope = CoroutineScope(Dispatchers.IO)

    private lateinit var dao: RatesDao
    private lateinit var db: RatesDatabase

    private lateinit var rate1: RateEntity
    private lateinit var rate2: RateEntity
    private lateinit var rate3: RateEntity
    private lateinit var ratesList: List<RateEntity>

    private lateinit var currency1: CurrencyEntity
    private lateinit var currency2: CurrencyEntity
    private lateinit var currency3: CurrencyEntity
    private lateinit var currenciesList: List<CurrencyEntity>

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().context
        db = Room.inMemoryDatabaseBuilder(
            context, RatesDatabase::class.java
        ).build()
        dao = db.ratesDao

        rate1 = RateEntity("RUB", 80.2, 80.2)
        rate2 = RateEntity("EUR", 1.0, 1.0)
        rate3 = RateEntity("USD", 1.05, 1.05)
        ratesList = listOf(rate1, rate2, rate3)

        currency1 = CurrencyEntity("RUB", R.drawable.ic_rub, R.string.RUB)
        currency2 = CurrencyEntity("EUR", R.drawable.ic_eur, R.string.EUR)
        currency3 = CurrencyEntity("USD", R.drawable.ic_usd, R.string.USD)
        currenciesList = listOf(currency1, currency2, currency3)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test(expected = SQLiteConstraintException::class)
    fun couldNotInsertRatesWithoutOperation() {
        dao.prepopulateCurrencies(currenciesList)
        dao.insertRates(ratesList)
    }

    @Test(expected = SQLiteConstraintException::class)
    fun couldNotInsertRatesWithoutCurrency() {
        val operationId = dao.insertOperation(OperationEntity(ratesDate = Date()))
        for (rate in ratesList) {
            rate.hostOperationId = operationId
        }
        dao.insertRates(ratesList)
    }

    @Test
    fun insertOperationAndRatesListTest() {
        scope.launch {
            val operationId = dao.insertOperation(OperationEntity(ratesDate = Date()))
            for (rate in ratesList) {
                rate.hostOperationId = operationId
            }
            dao.prepopulateCurrencies(currenciesList)
            dao.insertRates(ratesList)
            val operation: CurrencyRatesList? = dao.getRates()
            val rates: List<RateCurrency>? = operation?.rates

            assertEquals(3, rates?.size)
        }
    }

    @Test
    fun clearOperationClearAllDataTest() {
        scope.launch {
            val operationId = dao.insertOperation(OperationEntity(ratesDate = Date()))
            for (rate in ratesList) {
                rate.hostOperationId = operationId
            }
            dao.prepopulateCurrencies(currenciesList)
            dao.insertRates(ratesList)
            var operation: CurrencyRatesList? = dao.getRates()
            val rates: List<RateCurrency>? = operation?.rates
            var currList: List<CurrencyEntity> = dao.getCurrenciesList()
            var onlyRates: List<RateEntity> = dao.getOnlyRates()

            assertEquals(3, rates?.size)
            assertTrue(operation != null)
            assertTrue(currList.isNotEmpty())
            assertTrue(onlyRates.isNotEmpty())

            dao.clearOperation()
            operation = dao.getRates()
            currList = dao.getCurrenciesList()
            onlyRates = dao.getOnlyRates()


            assertTrue(operation == null)
            assertTrue(currList.isNotEmpty())
            assertTrue(onlyRates.isEmpty())
        }

    }


}