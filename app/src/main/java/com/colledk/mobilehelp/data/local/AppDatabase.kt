package com.colledk.mobilehelp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.colledk.mobilehelp.data.local.converters.DateConverter
import com.colledk.mobilehelp.data.local.model.UserLocal

@Database(entities = [UserLocal::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}