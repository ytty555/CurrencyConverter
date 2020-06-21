package ru.okcode.currencyconverter.model.db

import androidx.room.*
import java.util.*

private const val COLUMN_RATES_DATE = "rates_date"
private const val COLUMN_RATE_TO_EURO = "rate_to_euro"
private const val COLUMN_RATE_TO_BASE = "rate_to_base"
private const val COLUMN_CURRENCY_CODE = "currency_code"
private const val COLUMN_OPERATION_ID = "operation_id"
private const val COLUMN_BASE_CURRENCY_CODE = "base_currency_code"
private const val COLUMN_BASE_CURRENCY_AMOUNT = "base_currency_amount"
private const val COLUMN_BASE_CURRENCY_RATE_TO_EURO = "base_currency_rate_to_euro"

@Entity(tableName = "operation_table")
data class DataSetEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_OPERATION_ID)
    val id: Long = 0L,

    @ColumnInfo(name = COLUMN_RATES_DATE)
    var actualDate: Date,

    @ColumnInfo(name = COLUMN_BASE_CURRENCY_CODE)
    var baseCurrencyCode: String = "EUR",

    @ColumnInfo(name = COLUMN_BASE_CURRENCY_RATE_TO_EURO)
    var baseCurrencyRateToEuro: Double,

    @ColumnInfo(name = COLUMN_BASE_CURRENCY_AMOUNT)
    var baseCurrencyAmount: Float = 1F

)

@Entity(
    tableName = "rate_table",
    foreignKeys = [
        ForeignKey(
            entity = DataSetEntity::class,
            parentColumns = [COLUMN_OPERATION_ID],
            childColumns = [COLUMN_OPERATION_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = [COLUMN_CURRENCY_CODE]),
        Index(value = [COLUMN_OPERATION_ID])
    ]
)
data class RateEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = COLUMN_CURRENCY_CODE)
    val currencyCode: String,

    @ColumnInfo(name = COLUMN_RATE_TO_EURO)
    var rateToEuro: Double,

    @ColumnInfo(name = COLUMN_RATE_TO_BASE)
    var rateToBase: Double,

    @ColumnInfo(name = COLUMN_OPERATION_ID)
    var hostOperationId: Long = 0L

)

data class CurrencyRatesList(
    @Embedded val dataSet: DataSetEntity,
    @Relation(
        entity = RateEntity::class,
        parentColumn = COLUMN_OPERATION_ID,
        entityColumn = COLUMN_OPERATION_ID
    )
    val rates: List<RateEntity>
)

