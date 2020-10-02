package ru.okcode.currencyconverter.data.repository

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.concurrent.TimeUnit

class RawRatesRepositoryImplTest {

    private val network = mock(NetworkRepository::class.java)
    private val cache = mock(CacheRepository::class.java)

    private val sut = RawRatesRepositoryImpl(
        network,
        cache
    )

    // (Cache => Data && Network => Data) ===> CacheData
    @Test
    fun when_CacheReturnData_NetworkReturnData_thenReturn_Cache() {
        // given
        val cacheRates = getRatesActualByData01()
        val networkRates = getRatesActualByData02()
        `when`(cache.getRates()).thenReturn(
            Single.just(cacheRates).delay(1, TimeUnit.MILLISECONDS)
        )

        `when`(cache.saveCache(any())).thenReturn(Completable.complete())

        `when`(network.getRates()).thenReturn(
            Single.just(networkRates).delay(5, TimeUnit.MILLISECONDS)
        )

        // when
        val testObserver = sut.getRatesSingle().test()


        // then
        testObserver.assertSubscribed()
            .awaitTerminalEvent()

        testObserver
            .assertNoErrors()
            .assertTerminated()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(cacheRates)
    }

    // (Network => Data && Cache => Data) ===> CacheData
    @Test
    fun when_NetworkReturnData_CacheReturnData_thenReturn_Cache() {
        // given
        val cacheRates = getRatesActualByData01()
        val networkRates = getRatesActualByData02()
        `when`(cache.getRates()).thenReturn(
            Single.just(cacheRates).delay(5, TimeUnit.MILLISECONDS)
        )

        `when`(cache.saveCache(any())).thenReturn(Completable.complete())

        `when`(network.getRates()).thenReturn(
            Single.just(networkRates).delay(1, TimeUnit.MILLISECONDS)
        )

        // when
        val testObserver = sut.getRatesSingle().test()


        // then
        testObserver.assertSubscribed()
            .awaitTerminalEvent()

        testObserver
            .assertNoErrors()
            .assertTerminated()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(cacheRates)

        verify(cache, times(0)).saveCache(networkRates)
    }

    // (Cache => OldData && Network => Data) ===> NetworkData && cache.saveCache(NetworkData)
    @Test
    fun when_CacheReturnOldData_NetworkReturnData_thenReturn_Network_andSaveCache() {
        // given
        val cacheOldRates = getRatesNOTActualByData01()
        val networkRates = getRatesActualByData02()
        `when`(cache.getRates()).thenReturn(
            Single.just(cacheOldRates).delay(1, TimeUnit.MILLISECONDS)
        )

        `when`(cache.saveCache(any())).thenReturn(Completable.complete())

        `when`(network.getRates()).thenReturn(
            Single.just(networkRates).delay(5, TimeUnit.MILLISECONDS)
        )

        // when
        val testObserver = sut.getRatesSingle().test()


        // then
        testObserver.assertSubscribed()
            .awaitTerminalEvent()

        testObserver
            .assertNoErrors()
            .assertTerminated()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(networkRates)

        verify(cache, times(1)).saveCache(networkRates)
    }

    // (Network => Data && Cache => OldData) ===> NetworkData && cache.saveCache(NetworkData)
    @Test
    fun when_NetworkReturnData_CacheReturnOldData_thenReturn_Network_andSaveCache() {
        // given
        val cacheOldRates = getRatesNOTActualByData01()
        val networkRates = getRatesActualByData02()
        `when`(cache.getRates()).thenReturn(
            Single.just(cacheOldRates).delay(5, TimeUnit.MILLISECONDS)
        )

        `when`(cache.saveCache(any())).thenReturn(Completable.complete())

        `when`(network.getRates()).thenReturn(
            Single.just(networkRates).delay(1, TimeUnit.MILLISECONDS)
        )

        // when
        val testObserver = sut.getRatesSingle().test()


        // then
        testObserver.assertSubscribed()
            .awaitTerminalEvent()

        testObserver
            .assertNoErrors()
            .assertTerminated()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(networkRates)

        verify(cache, times(1)).saveCache(networkRates)
    }

    // (Cache => EmptyError && Network => Data) ===> NetworkData && cache.saveCache(NetworkData)
    @Test
    fun when_CacheReturnError_NetworkReturnData_thenReturn_Network_andSaveCache() {
        // given
        val networkRates = getRatesActualByData02()
        `when`(cache.getRates()).thenReturn(
            Single.error(Throwable())
        )

        `when`(cache.saveCache(any())).thenReturn(Completable.complete())

        `when`(network.getRates()).thenReturn(
            Single.just(networkRates).delay(5, TimeUnit.MILLISECONDS)
        )

        // when
        val testObserver = sut.getRatesSingle().test()


        // then
        testObserver.assertSubscribed()
            .awaitTerminalEvent()

        testObserver
            .assertNoErrors()
            .assertTerminated()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(networkRates)

        verify(cache, times(1)).saveCache(networkRates)
    }

    // (Cache => OldData && Network => Error) ===> Error
    @Test
    fun when_CacheReturnOldData_NetworkReturnError_thenReturn_Error_andCacheClean() {
        // given
        val cacheOldRates = getRatesNOTActualByData01()
        val exception = Throwable("Some error")
        `when`(cache.getRates()).thenReturn(
            Single.just(cacheOldRates).delay(5, TimeUnit.MILLISECONDS)
        )

        `when`(cache.saveCache(any())).thenReturn(Completable.complete())

        `when`(network.getRates()).thenReturn(
            Single.error(exception)
        )

        // when
        val testObserver = sut.getRatesSingle().test()


        // then
        testObserver.assertSubscribed()
            .awaitTerminalEvent()

        testObserver
            .assertError(exception)
            .assertTerminated()
            .assertNotComplete()
            .assertNoValues()

        verify(cache, times(0)).saveCache(any())
    }

    // (Cache => Error && Network => Error) ===> Error
    @Test
    fun when_CacheReturnError_NetworkReturnError_thenReturn_Error() {
        // given
        val exceptionCache = Throwable("Cache error")
        val exceptionNetwork = Throwable("Network error")
        `when`(cache.getRates()).thenReturn(
            Single.error(exceptionCache)
        )

        `when`(cache.saveCache(any())).thenReturn(Completable.complete())

        `when`(network.getRates()).thenReturn(
            Single.error(exceptionNetwork)
        )

        // when
        val testObserver = sut.getRatesSingle().test()


        // then
        testObserver.assertSubscribed()
            .awaitTerminalEvent()

        testObserver
            .assertError(exceptionNetwork)
            .assertTerminated()
            .assertNotComplete()
            .assertNoValues()

        verify(cache, times(0)).saveCache(any())
    }
}