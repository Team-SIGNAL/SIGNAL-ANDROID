package com.signal.domain.usecase.users

import com.signal.domain.enums.Gender
import com.signal.domain.repository.UserRepository

class SignUpUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        name: String,
        birth: String,
        phone: String,
        accountId: String,
        password: String,
        gender: Gender,
    ) = runCatching {
        userRepository.signUp(
            name = name,
            birth = birth,
            phone = phone,
            accountId = accountId,
            password = password,
            gender = gender,
        )
    }
}
