package ru.okcode.currencyconverter.model.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.okcode.currencyconverter.model.CommonRates
import java.io.IOException
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class DbWorkTest {
    private lateinit var workDao: RatesDao
    private lateinit var db: DbWork

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, DbWork::class.java).build()
        workDao = db.workRatesDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeCommonRatesAndReadReadRatesListSize() {
        val commonRates: CommonRates = TestHelperDbWork().createTestCommonRatesBaseEuro()

        workDao.safeRates(commonRates)

        workDao.getRates()
            .subscribe{
                val countRates = it?.rates?.size
                assertEquals(3, commonRates)
            }
    }

}