package com.colledk.mobilehelp.domain.usecase

import com.colledk.mobilehelp.domain.model.User
import com.colledk.mobilehelp.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(id: String): User {
        return userRepository.getUser(id = id)
    }
}