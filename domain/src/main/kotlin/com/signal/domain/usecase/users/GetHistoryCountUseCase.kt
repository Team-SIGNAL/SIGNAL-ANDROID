package com.signal.domain.usecase.users

import com.signal.domain.repository.DiagnosisRepository

class GetHistoryCountUseCase(
    private val diagnosisRepository: DiagnosisRepository,
) {
    suspend operator fun invoke() = runCatching {
        diagnosisRepository.getHistoryCount()
    }
}