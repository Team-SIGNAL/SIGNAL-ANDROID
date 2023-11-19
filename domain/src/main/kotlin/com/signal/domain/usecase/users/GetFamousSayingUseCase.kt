package com.signal.domain.usecase.users

import com.signal.domain.repository.UserRepository

class GetFamousSayingUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(id: Long) = kotlin.runCatching {
        userRepository.getFamousSaying(id = id)
    }
}