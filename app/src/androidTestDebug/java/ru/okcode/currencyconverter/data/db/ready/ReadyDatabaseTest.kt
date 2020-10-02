package ru.okcode.currencyconverter.data.db.ready

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ReadyDatabaseTest {
    private lateinit var readyDb: ReadyDatabase
    private lateinit var readyDao: ReadyDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        readyDb = Room.inMemoryDatabaseBuilder(
            context, ReadyDatabase::class.java
        ).build()
        readyDao = readyDb.readyDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        readyDb.close()
    }

    @Test
    fun test1() {
        fail()
    }
}

