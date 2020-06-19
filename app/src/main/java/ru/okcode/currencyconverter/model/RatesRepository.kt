package ru.okcode.currencyconverter.model

import io.reactivex.Observable
import ru.okcode.currencyconverter.model.api.RatesData

interface RatesRepository {

    fun getRatesDataSource(forceUpdate: Boolean): Observable<RatesData>



}
