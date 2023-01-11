package com.colledk.mobilehelp.domain.usecase

import com.colledk.mobilehelp.domain.model.User
import com.colledk.mobilehelp.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ListenToUsersUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Flow<List<User>> {
        return userRepository.listenToUsers()
    }
}