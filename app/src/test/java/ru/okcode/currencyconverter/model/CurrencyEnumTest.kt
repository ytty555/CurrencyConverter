package ru.okcode.currencyconverter.model

import org.junit.Assert.*
import org.junit.Test
import ru.okcode.currencyconverter.R

class CurrencyEnumTest {

    @Test
    fun testGetEnumByCurrencyCode() {
        val expected = CurrencyEnum.EUR
        val actual = CurrencyEnum.valueOf("EUR")
        assertEquals(expected, actual)
    }

    @Test
    fun testGetResourcesByCurrencyCode() {
        val expected1 = R.drawable.ic_eur
        val actual1 = CurrencyEnum.valueOf("EUR").flagRes
        val expected2 = R.string.EUR
        val actual2 = CurrencyEnum.valueOf("EUR").fullNameRes
        assertEquals("Wrong flag resource", expected1, actual1)
        assertEquals("Wrong fullName resource", expected2, actual2)
    }

    @Test
    fun testToStringValue() {
        val expected = "EUR"
        val actual = CurrencyEnum.EUR.toString()
        assertEquals(expected, actual)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testWrongCurrencyCode() {
        CurrencyEnum.valueOf("RUUUB")
    }
}