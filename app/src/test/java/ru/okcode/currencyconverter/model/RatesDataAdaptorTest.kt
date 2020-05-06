package ru.okcode.currencyconverter.model

import org.junit.Assert.*
import org.junit.Test
import ru.okcode.currencyconverter.TestUtils

class RatesDataAdaptorTest {

    @Test
    fun testConvert() {
        val expected: RatesData = TestUtils.testObjRatesData
        val actual: RatesData =
            RatesDataAdaptor.convertToRatesData(TestUtils.testObjCurrencyRatesList)
        assertEquals(expected, actual)
    }

}