package ru.okcode.currencyconverter.model

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Deferred
import ru.okcode.currencyconverter.model.api.RatesDto
import ru.okcode.currencyconverter.model.db.CacheDatabase
import ru.okcode.currencyconverter.util.Result

interface Repository {
    val rates: LiveData<Rates>
}