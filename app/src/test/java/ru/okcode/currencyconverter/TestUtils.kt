package ru.okcode.currencyconverter

import ru.okcode.currencyconverter.model.Rate
import ru.okcode.currencyconverter.model.RatesData
import ru.okcode.currencyconverter.model.db.CurrencyRatesList
import ru.okcode.currencyconverter.model.db.OperationEntity
import ru.okcode.currencyconverter.model.db.RateEntity
import java.util.*

class TestUtils {
    companion object {
        val testObjRatesList: List<RateEntity> = listOf(
            RateEntity("RUB", 80.22, 80.22),
            RateEntity("EUR", 1.0, 1.0),
            RateEntity("USD", 1.02, 1.02)
        )

        val testObjRateList: List<Rate> = listOf(
            Rate("RUB", R.drawable.ic_rub, R.string.RUB, 80.22, 80.22),
            Rate("EUR", R.drawable.ic_eur, R.string.EUR, 1.0, 1.0),
            Rate("USD", R.drawable.ic_usd, R.string.USD, 1.02, 1.02)
        )

        val testObjOperationEntity: OperationEntity = OperationEntity(ratesDate = Date())

        val testObjCurrencyRatesList: CurrencyRatesList =
            CurrencyRatesList(testObjOperationEntity, testObjRatesList)


        val testObjRatesData: RatesData = RatesData(
            testObjOperationEntity.ratesDate,
            testObjOperationEntity.baseCurrency,
            testObjRateList
        )
    }
}