package ru.okcode.currencyconverter.data.repository

import io.reactivex.Observable
import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.Rates

interface ReadyRepository {

    fun getAllRates(): Observable<Rates>
}