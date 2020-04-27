package ru.okcode.currencyconverter.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "rates_table")
data class Rates(
    @PrimaryKey(autoGenerate = true)
    val ratesId: Int,

    var ratesDate: Date = Date(),

    var values: List<CurrencyRate>
)