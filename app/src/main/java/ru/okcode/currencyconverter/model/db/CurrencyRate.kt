package ru.okcode.currencyconverter.model.db

import androidx.room.*

private const val COLUMN_ID = "id"
private const val COLUMN_RATES_DATE = "rates_date"
private const val COLUMN_RATE_TO_EURO = "rate_to_euro"
private const val COLUMN_CURRENCY_CODE = "currency_code"
private const val COLUMN_CURRENCY_FULL_NAME_RES = "currency_name_resource"
private const val COLUMN_CURRENCY_FLAG_RES = "currency_flag_resource"

@Entity(tableName = "rates_list_table")
data class RatesListEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Long = 0L,

    @ColumnInfo(name = COLUMN_RATES_DATE)
    var ratesDate: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "rate_table",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = CurrencyEntity::class,
            parentColumns = arrayOf(COLUMN_CURRENCY_CODE),
            childColumns = arrayOf(COLUMN_CURRENCY_CODE),
            onDelete = ForeignKey.CASCADE
        )
    ),
    indices = [Index(value = [COLUMN_CURRENCY_CODE])]
)
data class RateEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = COLUMN_CURRENCY_CODE)
    val currencyCode: String,

    @ColumnInfo(name = COLUMN_RATE_TO_EURO)
    var rateToEuro: Double,

    val hostRatesList: Long

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

data class CurrencyRate(
    @Embedded val currency: CurrencyEntity,
    @Relation(
        parentColumn = COLUMN_CURRENCY_CODE,
        entityColumn = COLUMN_CURRENCY_CODE
    )
    val rate: RateEntity

)

//data class CurrencyRatesList(
//    @Embedded val ratesList: RatesList,
//    @Relation(
//        entity = CurrencyRate::class,
//        parentColumn = "ratesId",
//        entityColumn = "hostRatesList"
//    )
//    val currencyRates: List<CurrencyRate>
//)

