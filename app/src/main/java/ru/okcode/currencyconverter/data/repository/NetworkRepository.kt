package ru.okcode.currencyconverter.data.repository

import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Rates

interface NetworkRepository {
    fun getRates(): Single<Rates>
}