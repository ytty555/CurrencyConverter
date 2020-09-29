package ru.okcode.currencyconverter.data.repository

import io.reactivex.Observable
import ru.okcode.currencyconverter.data.model.Rates

interface RawRatesRepository {
    fun getRates(): Observable<Rates>
}