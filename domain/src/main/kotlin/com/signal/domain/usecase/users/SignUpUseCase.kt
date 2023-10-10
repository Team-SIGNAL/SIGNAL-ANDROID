package com.signal.domain.usecase.users

import com.signal.domain.repository.UserRepository
import java.time.LocalDate

class SignUpUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        name: String,
        birth: LocalDate,
        phone: String,
        accountId: String,
        password: String,
    ) = runCatching {
        userRepository.signUp(
            name = name,
            birth = birth,
            phone = phone,
            accountId = accountId,
            password = password,
        )
    }
}
