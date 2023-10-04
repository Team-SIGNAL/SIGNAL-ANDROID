package com.signal.domain.usecase

import com.signal.domain.repository.UserRepository

class SignInUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        accountId: String,
        password: String,
    ) = kotlin.runCatching {
        userRepository.signIn(
            accountId = accountId,
            password = password,
        )
    }
}