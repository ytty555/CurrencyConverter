package ru.okcode.currencyconverter.data.repository

import androidx.room.EmptyResultSetException
import com.nhaarman.mockitokotlin2.doThrow
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import ru.okcode.currencyconverter.data.db.cache.CacheDao
import ru.okcode.currencyconverter.data.db.cache.CacheMapper
import ru.okcode.currencyconverter.data.model.Rates

class CacheRepositoryImplTest {
    private val mockCacheDao = mock(CacheDao::class.java)
    private val cacheMapper = CacheMapper()

    private val sut = CacheRepositoryImpl(
        cacheDao = mockCacheDao,
        cacheMapper = cacheMapper
    )

    @Test
    fun getRates_getGoodData_returnGoodSingleRates() {
        // given
        val testObserver = TestObserver<Rates>()
        val ratesFromCacheDao = getRatesActualByData01()
        val cacheHeaderWithRates = cacheMapper.mapToEntity(ratesFromCacheDao)
        `when`(mockCacheDao.getCacheFlowable()).thenReturn(Single.just(cacheHeaderWithRates))

        // when
        val observable = sut.getRatesObservable()
        observable.subscribe(testObserver)

        // then
        testObserver.assertSubscribed()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
            .assertComplete()
            .assertValue(ratesFromCacheDao)
            .assertValueCount(1)
    }

    @Test
    fun getRates_getError_returnError() {
        // given
        val testObserver = TestObserver<Rates>()
        val exception = EmptyResultSetException("123")
        `when`(mockCacheDao.getCacheFlowable()).thenReturn(Single.error(exception))

        // when
        val observable = sut.getRatesObservable()
        observable.subscribe(testObserver)

        // then
        testObserver.assertSubscribed()
        testObserver.awaitTerminalEvent()
        testObserver.assertError(exception)
            .assertNotComplete()
            .assertNoValues()
            .assertTerminated()
    }

    @Test
    fun saveCache_cacheDaoInsertError() {
        // given
        val exception = EmptyResultSetException("123")
        val rates = getRatesActualByData01()
        val cacheEntity = cacheMapper.mapToEntity(rates)
        doThrow(exception).`when`(mockCacheDao).insertToCache(cacheEntity)

        // when
        val testObserver = sut.saveCache(rates)
            .test()

        // then
        testObserver.assertSubscribed()
            .awaitTerminalEvent()

        testObserver
            .assertNotComplete()
            .assertError(exception)
            .assertNoValues()
            .assertTerminated()
    }

    @Test
    fun saveCache_cacheDaoInsertOk() {
        // given
        val rates = getRatesActualByData01()
        val testObserver = TestObserver<Void>()

        // when
        val observer = sut.saveCache(rates)
        observer.subscribe(testObserver)

        // then
        testObserver.assertSubscribed()
            .awaitTerminalEvent()

        testObserver
            .assertComplete()
            .assertNoValues()
            .assertNoErrors()
    }
}