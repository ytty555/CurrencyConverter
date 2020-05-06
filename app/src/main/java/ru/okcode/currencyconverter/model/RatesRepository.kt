package ru.okcode.currencyconverter.model

import android.content.Context
import ru.okcode.currencyconverter.model.db.RatesDatabase

interface RatesRepository {

    suspend fun getRates(forceUpdate: Boolean): Result<RatesData>

}
