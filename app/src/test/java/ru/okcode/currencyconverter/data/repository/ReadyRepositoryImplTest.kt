package ru.okcode.currencyconverter.data.repository

import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import ru.okcode.currencyconverter.data.db.ready.ReadyRates

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
        `when`(mockReadyRates.getReadyRates()).thenReturn(Single.error(readyException))
        `when`(mockCacheRepository.getRates()).thenReturn(Single.just(cacheRates))

        // when
        val testObserver = sut.getRates().test()

        // then
        testObserver.assertSubscribed()
            .awaitTerminalEvent()
        testObserver
            .assertValue(readyRates)
            .assertValueCount(1)
            .assertComplete()
            .assertTerminated()
            .assertNoErrors()
    }

    /**
     * ReadyRates => Error
     * Cache => Error
     * Result => Error
     */
    @Test
    fun getRates_when_ReadyReturnError_CacheReturnError_then_Error() {
        // given
        val readyException = Throwable("123")
        val cacheException = Throwable("321")
        `when`(mockReadyRates.getReadyRates()).thenReturn(Single.error(readyException))
        `when`(mockCacheRepository.getRates()).thenReturn(Single.error(cacheException))

        // when
        val testObserver = sut.getRates().test()

        // then
        testObserver.assertSubscribed()
            .awaitTerminalEvent()
        testObserver
            .assertNotComplete()
            .assertTerminated()
            .assertError(cacheException)
    }
}