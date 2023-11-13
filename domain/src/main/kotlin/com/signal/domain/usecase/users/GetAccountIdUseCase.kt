package com.signal.domain.usecase.users

import com.signal.domain.repository.UserRepository

class GetAccountIdUseCase(
    private val userRepository: UserRepository,
) {
    operator fun invoke() = kotlin.runCatching {
        userRepository.getAccountId()
    }
}