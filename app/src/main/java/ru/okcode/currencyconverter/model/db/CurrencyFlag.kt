package ru.okcode.currencyconverter.model.db

import android.content.res.Resources
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flag_table")
data class CurrencyFlag(
    @PrimaryKey(autoGenerate = true)
    val flagId: Long,

    val currencyShortName: String,

    @ColumnInfo(name = "flag_img_resource")
    val FlagRes: Resources
)