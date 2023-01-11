package com.colledk.mobilehelp.data

import com.colledk.mobilehelp.data.local.AppDatabase
import com.colledk.mobilehelp.data.remote.UserDatabaseRemote
import com.colledk.mobilehelp.domain.model.User
import com.colledk.mobilehelp.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.Date

class UserRepositoryImpl(
    private val database: AppDatabase,
    private val remoteDatabase: UserDatabaseRemote,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository {
    override suspend fun getUsers(): List<User> = withContext(dispatcher) {
        return@withContext remoteDatabase.getUsers().map { it.mapToDomain() }
    }

    override suspend fun getUser(id: String): User = withContext(dispatcher) {
        return@withContext database.userDao().getUsers().firstOrNull()?.mapToDomain()
            ?: remoteDatabase.getUser(id).mapToDomain()
    }

    override suspend fun saveUser(user: User) = withContext(dispatcher) {
        remoteDatabase.storeUser(user = user.mapToRemote())
        database.userDao().insertUser(userLocal = user.mapToLocal())
    }

    override suspend fun listenToUsers(): Flow<List<User>> {
        return remoteDatabase.listenToUsersFlow()
            .map { flow -> flow.map { user -> user.mapToDomain() } }
    }
}