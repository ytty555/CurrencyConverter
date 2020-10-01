package ru.okcode.currencyconverter.data.ready

import org.junit.Before
import org.junit.Test
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.data.repository.getRatesActualByData01
import java.lang.NullPointerException

class ReadyRatesImplTest {

    private lateinit var sut: ReadyRatesImpl

    @Before
    fun setUp() {
        sut = ReadyRatesImpl()
    }

    @Test
    fun when_ratesIsNotNull_thenReturn_Rates() {
        // given
        val rates: Rates = getRatesActualByData01()
        sut.setReadyRates(rates)

        // when
        val testObserver = sut.getReadyRates().test()

        // then
        testObserver.assertSubscribed()
            .awaitTerminalEvent()

        testObserver
            .assertComplete()
            .assertNoErrors()
            .assertTerminated()
            .assertValueCount(1)
            .assertValue(rates)
    }

    @Test
    fun when_ratesIsNull_thenReturn_Error() {
        // given

        // when
        val testObserver = sut.getReadyRates().test()

        // then
        testObserver.assertSubscribed()
            .awaitTerminalEvent()

        testObserver
            .assertError(NullPointerException()::class.java)
            .assertTerminated()
            .assertNotComplete()
            .assertNoValues()

    }
}