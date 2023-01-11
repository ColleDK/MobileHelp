package com.colledk.mobilehelp.domain.repository

import com.colledk.mobilehelp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getUser(id: String): User
    suspend fun saveUser(user: User)
    suspend fun listenToUsers(): Flow<List<User>>
}