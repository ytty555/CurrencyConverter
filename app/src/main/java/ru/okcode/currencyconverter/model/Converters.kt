package ru.okcode.currencyconverter.model

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromDateToLong(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromLongToDate(value: Long?): Date? {
        return value?.let {
            Date(it)
        }
    }
}