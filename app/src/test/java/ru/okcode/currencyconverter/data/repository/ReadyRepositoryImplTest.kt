package ru.okcode.currencyconverter.data.repository

import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.junit.Assert.fail
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.data.ready.ReadyRates
import ru.okcode.currencyconverter.data.ready.ReadyRatesImpl
import kotlin.test.assertEquals

class ReadyRepositoryImplTest {

    //Mock
    private val mockReadyRates = mock(ReadyRates::class.java)
    private val mockCacheRepository = mock(CacheRepository::class.java)

    //SUT
    private val sut = ReadyRepositoryImpl(
        mockReadyRates,
        mockCacheRepository
    )

    /**
     * ReadyRates => ReadyData
     * Cache => CacheData
     * Result => ReadyData
     */
    @Test
    fun getRates_when_ReadyReturnData_CacheReturnData_then_ReadyData() {
        // given
        val readyRates = getRatesActualByData01()
        val cacheRates = getRatesActualByData02()
        `when`(mockReadyRates.getReadyRates()).thenReturn(Single.just(readyRates))
        `when`(mockCacheRepository.getRates()).thenReturn(Single.just(cacheRates))

        // when
        val testObserver = sut.getRates().test()

        // then
        testObserver.assertSubscribed()
            .awaitTerminalEvent()
        testObserver
            .assertNoErrors()
            .assertComplete()
            .assertTerminated()
            .assertValueCount(1)
            .assertValue(readyRates)
    }

    /**
     * ReadyRates => ReadyData
     * Cache => Error
     * Result => ReadyData
     */
    @Test
    fun getRates_when_ReadyReturnData_CacheReturnError_then_ReadyData() {
        // given
        val readyRates = getRatesActualByData01()
        val cacheException = Throwable()
        `when`(mockReadyRates.getReadyRates()).thenReturn(Single.just(readyRates))
        `when`(mockCacheRepository.getRates()).thenReturn(Single.error(cacheException))

        // when
        val testObserver = sut.getRates().test()

        // then
        testObserver.assertSubscribed()
            .awaitTerminalEvent()
        testObserver
            .assertNoErrors()
            .assertComplete()
            .assertTerminated()
            .assertValueCount(1)
            .assertValue(readyRates)
    }

    /**
     * ReadyRates => Error
     * Cache => CacheData
     * Result => CacheData to Ready => ReadyData
     */
    @Test
    fun getRates_when_ReadyReturnError_CacheReturnData_then_CreatedReadyData() {
        // given
        val cacheRates = getRatesActualByData01()
        val readyRates = getRatesActualByData02()
        val readyException = Throwable()
        `when`(mockCacheRepository.getRates()).thenReturn(Single.just(cacheRates))

        `when`(mockReadyRates.getReadyRates()).thenAnswer(object : Answer<Single<Rates>> {
            var count = 1

            override fun answer(invocation: InvocationOnMock?): Single<Rates> {
                if (count++ == 1) {
                    return Single.error(readyException)
                }
                return Single.just(readyRates)
            }

        })


        // when
        val actual = sut.getRates().blockingGet()

        // then
        assertEquals(readyRates, actual)
    }

    /**
     * ReadyRates => Error
     * Cache => Error
     * Result => Error
     */
    @Test
    fun getRates_when_ReadyReturnError_CacheReturnError_then_Error() {
        // given

        // when

        // then
        fail()
    }
}