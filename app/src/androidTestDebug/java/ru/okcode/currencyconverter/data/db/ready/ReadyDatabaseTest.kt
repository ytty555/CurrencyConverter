package ru.okcode.currencyconverter.data.db.ready

import android.content.Context
import androidx.room.EmptyResultSetException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ReadyDatabaseTest {
    private lateinit var db: ReadyDatabase
    private lateinit var dao: ReadyDao

    private val testHelper = ReadyTestHelper()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ReadyDatabase::class.java
        ).build()
        dao = db.readyDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    /**
     * insert()
     */
    @Test
    fun insertTest() {
        // given
        val entity = testHelper.getReadyHeaderWithRates01()
        // when
        dao.insert(entity)
        val actual = dao.getEntityForTest()

        // then
        assertEquals(entity, actual)
    }

    /**
     * getReadyRatesSingle() with data
     * return Single data
     */
    @Test
    fun getReadyRatesSingle_DbWithData() {
        // given
        val entity = testHelper.getReadyHeaderWithRates01()
        // when
        dao.insert(entity)
        val testObserver = dao.getReadyRatesSingle().test()

        // then
        testObserver.assertSubscribed()
            .awaitTerminalEvent()
        testObserver
            .assertValue(entity)
            .assertValueCount(1)
            .assertNoErrors()
            .assertComplete()
    }

    /**
     * getReadyRatesSingle() with empty db
     * return Single error
     */
    @Test
    fun getReadyRatesSingle_DbEmpty() {
        // given

        // when
        val testObserver = dao.getReadyRatesSingle().test()

        // then
        testObserver.assertSubscribed()
            .awaitTerminalEvent()
        testObserver
            .assertNoValues()
            .assertError(EmptyResultSetException::class.java)
            .assertNotComplete()
            .assertTerminated()
    }
}

