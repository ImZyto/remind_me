package com.example.remin.model.converters

import androidx.room.TypeConverter
import java.util.*

class Converter {
    @TypeConverter
    fun fromTimestamp(value: Long): Date = Calendar.getInstance().apply { timeInMillis = value }.time


    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}