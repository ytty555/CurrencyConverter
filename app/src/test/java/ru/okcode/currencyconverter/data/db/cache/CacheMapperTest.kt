package ru.okcode.currencyconverter.data.db.cache

import org.junit.Assert.*
import org.junit.Test
import ru.okcode.currencyconverter.data.repository.getRatesActualByData01

class CacheMapperTest {

    private val sut = CacheMapper()

    @Test
    fun ratesToEntityToRatesTest_equalRatesInAndOut() {
        // given
        val rates = getRatesActualByData01()

        // when
        val entity = sut.mapToEntity(rates)
        val actualRates = sut.mapToModel(entity)

        // then
        assertEquals(rates, actualRates)
    }
}