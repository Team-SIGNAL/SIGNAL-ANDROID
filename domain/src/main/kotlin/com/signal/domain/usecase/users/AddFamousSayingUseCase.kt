package com.signal.domain.usecase.users

import com.signal.domain.entity.FamousSayingEntity
import com.signal.domain.repository.UserRepository

class AddFamousSayingUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(famousSayingEntities: List<FamousSayingEntity>) = kotlin.runCatching {
        userRepository.addFamousSaying(famousSayings = famousSayingEntities)
    }
}