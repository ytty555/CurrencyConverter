package ru.okcode.currencyconverter.model.db.ready

import android.icu.math.BigDecimal
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import ru.okcode.currencyconverter.model.ModelMapper
import ru.okcode.currencyconverter.model.Rate
import ru.okcode.currencyconverter.model.Rates
import ru.okcode.currencyconverter.util.getFlagRes

@Entity
data class ReadyHeader(
    val baseCurrencyCode: String,
    val baseCurrencyAmount: Double = 1.0,
    val baseCurrencyRateToEuro: BigDecimal,
    @PrimaryKey val timeLastUpdateUnix: Long,
    val timeNextUpdateUnix: Long
)

@Entity
data class ReadyRate(
    @PrimaryKey val currencyCode: String,
    val rateToBase: BigDecimal,
    val rateToEuro: BigDecimal,
    val sum: BigDecimal,
    val timeLastUpdateUnix: Long,
    val priorityPosition: Int
)

data class ReadyHeaderWithRates(
    @Embedded val readyHeader: ReadyHeader,
    @Relation(
        parentColumn = "timeNextUpdateUnix",
        entityColumn = "timeLastUpdateUnix"
    )
    val rates: List<ReadyRate>
): ModelMapper<ReadyHeaderWithRates, Rates> {

    override fun toModel(entity: ReadyHeaderWithRates): Rates {

        val rates: List<Rate> = entity.rates.map {readyRate ->
            Rate(
                currencyCode = readyRate.currencyCode,
                rateToBase = readyRate.rateToBase,
                rateToEur = readyRate.rateToEuro,
                sum = readyRate.sum,
                priorityPosition = readyRate.priorityPosition,
                flagRes = getFlagRes(readyRate.currencyCode)
            )
        }

        return Rates(
            baseCurrencyCode = entity.readyHeader.baseCurrencyCode,
            baseCurrencyAmount = entity.readyHeader.baseCurrencyAmount,
            baseCurrencyRateToEuro = entity.readyHeader.baseCurrencyRateToEuro,
            rates = rates,
            timeLastUpdateUnix = entity.readyHeader.timeLastUpdateUnix,
            timeNextUpdateUnix = entity.readyHeader.timeNextUpdateUnix
        )
    }
}