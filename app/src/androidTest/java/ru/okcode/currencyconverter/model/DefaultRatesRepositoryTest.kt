package ru.okcode.currencyconverter.model

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.model.api.ApiService
import ru.okcode.currencyconverter.model.api.DefaultApiService
import ru.okcode.currencyconverter.model.db.*
import java.util.*
import kotlin.coroutines.coroutineContext

@RunWith(AndroidJUnit4::class)
class DefaultRatesRepositoryTest {

    private lateinit var db: RatesDatabase
    private lateinit var dao: RatesDao

    private lateinit var currency1: CurrencyEntity
    private lateinit var currency2: CurrencyEntity
    private lateinit var currency3: CurrencyEntity
    private lateinit var currenciesList: List<CurrencyEntity>
//
//    private val mockCurrencyRatesList: CurrencyRatesList = mock(CurrencyRatesList::class.java)

    private val rateRemoteDataSource: ApiService = DefaultApiService()

//    private val repository: RatesRepository = DefaultRatesRepository(rateRemoteDataSource, dao)
    private lateinit var repository: RatesRepository

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().context
        db = Room.inMemoryDatabaseBuilder(
            context, RatesDatabase::class.java
        )
//            .allowMainThreadQueries()
            .build()
        dao = db.ratesDao

        currency1 = CurrencyEntity("RUB", R.drawable.ic_rub, R.string.RUB)
        currency2 = CurrencyEntity("EUR", R.drawable.ic_eur, R.string.EUR)
        currency3 = CurrencyEntity("USD", R.drawable.ic_usd, R.string.USD)
        currenciesList = listOf(currency1, currency2, currency3)

        dao.prepopulateCurrencies(currenciesList)

        repository = DefaultRatesRepository(rateRemoteDataSource, dao)
    }

    @Test
    fun rateRemoteDataSourceTest() {
        runBlocking {
            val ratesData: RatesData
            val response: Result<RatesData> = rateRemoteDataSource.getRates()
            if (response is Result.Success) {
                ratesData = (response as Result.Success<RatesData>).data
                assertEquals(80.5, ratesData.rates[0].rateToEuro, 0.0)
            } else fail("Test is fail! May be CurrencyEntity was not prepopulate. $response")
        }
    }

    @Test
    fun getRatesTest() {
        runBlocking {
            val ratesData: RatesData
            val testData = repository.getRates(true)
            if (testData is Result.Success) {
                ratesData = testData.data
                assertEquals(80.5, ratesData.rates[0].rateToEuro, 0.0)

                // check database
                val crl: CurrencyRatesList? = dao.getRates()
                assertEquals(80.5, crl!!.rates[0].rate.rateToEuro, 0.0)
            } else fail("Test is fail! May be CurrencyEntity was not prepopulate. $testData")
        }
    }


}