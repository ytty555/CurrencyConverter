package ru.okcode.currencyconverter.data.repository

import io.reactivex.Flowable
import io.reactivex.Single
import ru.okcode.currencyconverter.data.model.Rates

interface RawRatesRepository {

    fun getRatesObservable(): Flowable<Rates>

    fun getRatesSingle(): Single<Rates>

    fun updateRawRates(nothingToUpdateMessageShow: Boolean): Single<UpdateStatus>

}

sealed class UpdateStatus {
    object Success : UpdateStatus()
    data class NotNeededToUpdate(val nothingToUpdateMessageShow: Boolean) : UpdateStatus()
}