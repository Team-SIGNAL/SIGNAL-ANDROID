package com.signal.domain.usecase.users

import com.signal.domain.repository.UserRepository

class SignOutUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke()
    = kotlin.runCatching {
        userRepository.signOut()
    }
}