package com.colledk.mobilehelp.data

import com.colledk.mobilehelp.data.local.AppDatabase
import com.colledk.mobilehelp.domain.model.User
import com.colledk.mobilehelp.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class UserRepositoryImpl(
    private val database: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository {
    override suspend fun getUser(): User = withContext(dispatcher) {
        return@withContext database.userDao().getUsers().first().mapToDomain()
    }

    override suspend fun saveUser(user: User) = withContext(dispatcher) {
        database.userDao().insertUser(userLocal = user.mapToLocal())
    }
}