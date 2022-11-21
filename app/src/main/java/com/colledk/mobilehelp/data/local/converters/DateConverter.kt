package com.colledk.mobilehelp.data.local.converters

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {
    @TypeConverter
    fun toDate(timestamp: Long): Date {
        return Date(timestamp)
    }

    @TypeConverter
    fun toLong(date: Date): Long {
        return date.time
    }
}