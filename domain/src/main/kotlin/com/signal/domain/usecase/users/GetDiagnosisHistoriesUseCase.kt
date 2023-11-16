package com.signal.domain.usecase.users

import com.signal.domain.repository.DiagnosisRepository

class GetDiagnosisHistoriesUseCase(
    private val diagnosisRepository: DiagnosisRepository,
) {
    suspend operator fun invoke(userId: String) = kotlin.runCatching {
        diagnosisRepository.getDiagnosisHistories(userId = userId)
    }
}