package ru.okcode.currencyconverter.model.api

import ru.okcode.currencyconverter.model.RatesData
import ru.okcode.currencyconverter.model.Result
import ru.okcode.currencyconverter.model.fakedata.FakeDataCreator

class DefaultApiService: ApiService {
    override suspend fun getRates(): Result<RatesData> {
        // TODO fix and implement this function
        return FakeDataCreator.createFakeRatesData()
    }
}