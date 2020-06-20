package ru.okcode.currencyconverter.model.dbWork

import androidx.room.*
import java.util.*

private const val COLUMN_SET_ID = "rates_set_id"
private const val COLUMN_ACTUAL_DATE = "actual_date"
private const val COLUMN_BASE_AMOUNT = "base_amount"
private const val COLUMN_BASE_CODE = "base_code"
private const val COLUMN_CODE = "currency_code"
private const val COLUMN_POSITION = "position_in_list"
private const val COLUMN_RATE = "rate_to_base"

@Entity(tableName = "work_set_table")
data class WorkRatesSet(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_SET_ID)
    val id: Long = 0L,

    @ColumnInfo(name = COLUMN_BASE_CODE)
    val baseCurrencyCode: String,

    @ColumnInfo(name = COLUMN_BASE_AMOUNT)
    val baseCurrencyAmount: Float,

    @ColumnInfo(name = COLUMN_ACTUAL_DATE)
    val actualData: Date
)

@Entity(
    tableName = "rates_item_table",
    foreignKeys = [
        ForeignKey(
            entity = WorkRatesSet::class,
            parentColumns = [COLUMN_SET_ID],
            childColumns = [COLUMN_SET_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = [COLUMN_SET_ID]),
        Index(value = [COLUMN_CODE])
    ]
)
data class WorkRatesItem(
    @ColumnInfo(name = COLUMN_SET_ID)
    var hostRatesSetId: Long = 0L,

    @ColumnInfo(name = COLUMN_CODE)
    val CurrencyCode: String,

    @ColumnInfo(name = COLUMN_POSITION)
    val positionInList: Int,

    @ColumnInfo(name = COLUMN_RATE)
    val rateToBase: Double
)

data class WorkRatesList(
    @Embedded val workSet: WorkRatesSet,
    @Relation(
        entity = WorkRatesItem::class,
        parentColumn = COLUMN_SET_ID,
        entityColumn = COLUMN_SET_ID
    )
    val workRates: List<WorkRatesItem>
)