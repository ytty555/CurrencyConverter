package ru.okcode.currencyconverter.data.repository

import io.reactivex.Observable
import ru.okcode.currencyconverter.data.model.Rates

interface NetworkRepository {
    fun getRates(): Observable<Rates>
}