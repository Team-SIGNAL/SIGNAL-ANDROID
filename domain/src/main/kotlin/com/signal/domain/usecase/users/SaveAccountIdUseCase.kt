package com.signal.domain.usecase.users

import com.signal.domain.repository.UserRepository

class SaveAccountIdUseCase(
    private val userRepository: UserRepository,
) {
    operator fun invoke(email: String) = kotlin.runCatching {
        userRepository.saveAccountId(email)
    }
}