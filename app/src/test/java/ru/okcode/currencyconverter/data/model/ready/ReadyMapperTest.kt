package ru.okcode.currencyconverter.data.model.ready

import org.junit.Assert.*
import org.junit.Test
import ru.okcode.currencyconverter.data.repository.getRatesActualByData01

class ReadyMapperTest {

    private val sut: ReadyMapper = ReadyMapper()

    @Test
    fun mapToFromTest() {
        // given
        val rates = getRatesActualByData01()

        // when
        val entity = sut.mapToEntity(rates)
        val actual = sut.mapToModel(entity)

        // then
        assertEquals(rates, actual)
    }
}