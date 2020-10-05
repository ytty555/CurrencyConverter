package ru.okcode.currencyconverter.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.ui.overview.OverviewResult

interface RawRatesRepository {

    fun getRatesObservable(): Flowable<Rates>

    fun getRatesSingle(): Single<Rates>

    fun updateRawRates(): Single<UpdateStatus>

}

sealed class UpdateStatus {
    object Success: UpdateStatus()
    object NotNeededToUpdate: UpdateStatus()
}