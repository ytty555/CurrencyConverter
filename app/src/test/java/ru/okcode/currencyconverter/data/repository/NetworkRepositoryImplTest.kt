package ru.okcode.currencyconverter.data.repository

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.data.network.ApiService
import ru.okcode.currencyconverter.data.network.RatesDtoToRatesMapper

@RunWith(MockitoJUnitRunner::class)
class NetworkRepositoryTest {

    private val ratesDtoToRatesMapper = RatesDtoToRatesMapper()
    private val mockApi: ApiService = Mockito.mock(ApiService::class.java)

    private val sut = NetworkRepositoryImpl(
        api = mockApi,
        ratesDtoToRatesMapper = ratesDtoToRatesMapper
    )

    private val testObserver = TestObserver<Rates>()

    @Test
    fun getRates_whenApiReturnGoodData() {
        // given
        val ratesDto = getRatesDtoActualByDate01()
        val expectedRates = ratesDtoToRatesMapper.mapToModel(ratesDto)
        val wrongRates = getRatesActualByData02()
        Mockito.`when`(mockApi.getRatesObservable()).thenReturn(Observable.just(ratesDto))

        // when
        val observable = sut.getRatesObservable()
        observable.subscribe(testObserver)

        //then
        testObserver.awaitTerminalEvent()
        testObserver
            .assertSubscribed()
            .assertComplete()
            .assertValueCount(1)
            .assertValue(expectedRates)
            .assertNoErrors()
    }

    @Test
    fun getRates_whenApiReturnError() {
        // given
        val error = Throwable("Api cann't return value")
        Mockito.`when`(mockApi.getRatesObservable()).thenReturn(Observable.error(error))

        // when
        val observable = sut.getRatesObservable()
        observable.subscribe(testObserver)

        // then
        testObserver.assertSubscribed()
        testObserver.awaitTerminalEvent()
        testObserver.assertError(error)
        testObserver.assertNotComplete()
        testObserver.assertNoValues()
        testObserver.assertValueCount(0)
        testObserver.assertTerminated()
    }

}