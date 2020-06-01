package ru.okcode.currencyconverter.model

import ru.okcode.currencyconverter.model.api.RatesData

interface RatesRepository {

    suspend fun getRates(forceUpdate: Boolean): RatesData?

}
