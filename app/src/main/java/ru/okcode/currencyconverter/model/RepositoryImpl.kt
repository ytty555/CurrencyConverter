package ru.okcode.currencyconverter.model

import kotlinx.coroutines.Deferred
import ru.okcode.currencyconverter.model.api.ApiService
import ru.okcode.currencyconverter.model.api.RatesDto
import ru.okcode.currencyconverter.util.Result
import java.lang.Exception
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: ApiService
) : Repository {
    override fun getRatesAsync(): Deferred<RatesDto> {
        return api.getRatesAsync()
    }
}