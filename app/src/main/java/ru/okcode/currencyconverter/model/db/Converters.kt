package ru.okcode.currencyconverter.model.db

import android.icu.math.BigDecimal
import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromDoubleToBigDecimal(value: Double?): BigDecimal? {
        return value?.let { BigDecimal.valueOf(value) }
    }

    @TypeConverter
    fun fromBigDecimalToDouble(value: BigDecimal?): Double? {
        return value?.let { value.toDouble() }
    }
}