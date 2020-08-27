package ru.okcode.currencyconverter.model

import kotlinx.coroutines.Deferred
import ru.okcode.currencyconverter.model.api.RatesDto
import ru.okcode.currencyconverter.util.Result

interface Repository {
    fun getRatesAsync(): Deferred<RatesDto>
}