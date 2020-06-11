package ru.okcode.currencyconverter.model

import io.reactivex.rxjava3.core.Observable
import ru.okcode.currencyconverter.model.api.RatesData

interface RatesRepository {

    fun getRawRatesData(forceUpdate: Boolean): Observable<RatesData>

}
