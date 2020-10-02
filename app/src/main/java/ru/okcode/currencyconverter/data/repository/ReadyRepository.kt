package ru.okcode.currencyconverter.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Rates

interface ReadyRepository {

    fun getRates(): Single<Rates>

    fun saveRates(rates: Rates): Single<Rates>
}