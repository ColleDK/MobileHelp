package com.colledk.mobilehelp.domain.repository

import com.colledk.mobilehelp.domain.model.User

interface UserRepository {
    suspend fun getUser(): User
    suspend fun saveUser(user: User)
}