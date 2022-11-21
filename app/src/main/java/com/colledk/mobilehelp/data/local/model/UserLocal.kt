package com.colledk.mobilehelp.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class UserLocal(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "user_id") var id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "gender") val gender: GenderLocal,
    @ColumnInfo(name = "birthday") val birthday: Date
) {
    enum class GenderLocal {
        MALE,
        FEMALE
    }
}