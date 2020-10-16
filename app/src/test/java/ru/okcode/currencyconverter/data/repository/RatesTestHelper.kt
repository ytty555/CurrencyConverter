package ru.okcode.currencyconverter.data.repository

import ru.okcode.currencyconverter.data.model.Rates
import ru.okcode.currencyconverter.data.network.RatesDtoToRatesMapper

private val ratesDtoToRatesMapper = RatesDtoToRatesMapper()

fun getRatesActualByData01(): Rates {
    val ratesDto = getRatesDtoActualByDate01()
    return ratesDtoToRatesMapper.mapToModel(ratesDto)!!
}

fun getRatesActualByData02(): Rates {
    val ratesDto = getRatesDtoActualByDate02()
    return ratesDtoToRatesMapper.mapToModel(ratesDto)!!
}

fun getRatesNOTActualByData01(): Rates {
    val ratesDto = getRatesDtoNOTActualByDate01()
    return ratesDtoToRatesMapper.mapToModel(ratesDto)!!
}

fun getRatesNOTActualByData02(): Rates {
    val ratesDto = getRatesDtoNOTActualByDate02()
    return ratesDtoToRatesMapper.mapToModel(ratesDto)!!
}