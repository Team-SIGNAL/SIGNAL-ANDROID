package com.signal.domain.usecase.users

import com.signal.domain.entity.UserInformationEntity
import com.signal.domain.repository.UserRepository

class UpdateUserInformationUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(userInformationEntity: UserInformationEntity) = kotlin.runCatching {
        userRepository.updateUserInformation(userInformationEntity = userInformationEntity)
    }
}