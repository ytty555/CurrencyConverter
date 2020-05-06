package ru.okcode.currencyconverter.model.fakedata

import ru.okcode.currencyconverter.R
import ru.okcode.currencyconverter.model.Rate
import ru.okcode.currencyconverter.model.RatesData
import ru.okcode.currencyconverter.model.Result
import java.util.*

class FakeDataCreator {
    companion object {
        fun createFakeRatesData(): Result<RatesData> {
            val rate1 = Rate("RUB", R.drawable.ic_rub,R.string.RUB, 80.5)
            val rate2 = Rate("EUR", R.drawable.ic_eur,R.string.EUR, 1.0)
            val rate3 = Rate("USD", R.drawable.ic_usd,R.string.USD, 1.02)
            val ratesList: List<Rate> = listOf(rate1, rate2, rate3)
            val date = Date()
            val fakeRatesData = RatesData(date, ratesList)
            return Result.Success(fakeRatesData)
        }
    }
}