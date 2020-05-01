package ru.okcode.currencyconverter.model.db

import androidx.room.*
import java.util.*

private const val COLUMN_RATES_DATE = "rates_date"
private const val COLUMN_RATE_TO_EURO = "rate_to_euro"
private const val COLUMN_CURRENCY_CODE = "currency_code"
private const val COLUMN_CURRENCY_FULL_NAME_RES = "currency_name_resource"
private const val COLUMN_CURRENCY_FLAG_RES = "currency_flag_resource"
private const val COLUMN_OPERATION_ID = "operation_id"

@Entity(tableName = "operation_table")
data class OperationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_OPERATION_ID)
    val id: Long = 0L,

    @ColumnInfo(name = COLUMN_RATES_DATE)
    var ratesDate: Date
)

@Entity(
    tableName = "rate_table",
    foreignKeys = [
        ForeignKey(
            entity = CurrencyEntity::class,
            parentColumns = [COLUMN_CURRENCY_CODE],
            childColumns = [COLUMN_CURRENCY_CODE],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = OperationEntity::class,
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

    @ColumnInfo(name = COLUMN_OPERATION_ID)
    var hostOperationId: Long = 0L

)

@Entity(tableName = "currency_table")
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = COLUMN_CURRENCY_CODE)
    val currencyCode: String,

    @ColumnInfo(name = COLUMN_CURRENCY_FLAG_RES)
    val flagRes: Int,

    @ColumnInfo(name = COLUMN_CURRENCY_FULL_NAME_RES)
    val fullNameRes: Int
)

data class RateCurrency(
    @Embedded val rate: RateEntity,
    @Relation(
        parentColumn = COLUMN_CURRENCY_CODE,
        entityColumn = COLUMN_CURRENCY_CODE
    )
    val currency: CurrencyEntity
)

data class CurrencyRatesList(
    @Embedded val operation: OperationEntity,
    @Relation(
        entity = RateEntity::class,
        parentColumn = COLUMN_OPERATION_ID,
        entityColumn = COLUMN_OPERATION_ID
    )
    val rates: List<RateCurrency>
)

