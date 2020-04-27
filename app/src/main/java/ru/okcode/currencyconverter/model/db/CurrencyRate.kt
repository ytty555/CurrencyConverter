package ru.okcode.currencyconverter.model.db

import android.content.res.Resources
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "currency_rates_table")
data class CurrencyRate(
    @PrimaryKey(autoGenerate = true)
    val currencyId: Long,

    @ColumnInfo(name = "short_name")
    val shortName: String,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    @ColumnInfo(name = "rate_to_euro")
    var RateToEuro: Double

)