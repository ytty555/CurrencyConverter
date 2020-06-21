package ru.okcode.currencyconverter.model

import io.reactivex.Observable

interface RatesRepository {

    fun getRatesDataSource(forceUpdate: Boolean): Observable<CommonRates>



}
