package com.colledk.mobilehelp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.colledk.mobilehelp.data.local.model.UserLocal

@Dao
interface UserDao {
    @Query("SELECT * FROM userlocal")
    fun getUsers(): List<UserLocal>

    @Query("SELECT * FROM userlocal WHERE user_id = :id")
    fun getUser(id: Int): UserLocal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userLocal: UserLocal)

    @Delete
    fun deleteUser(userLocal: UserLocal)
}