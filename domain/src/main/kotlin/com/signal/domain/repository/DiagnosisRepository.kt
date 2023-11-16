package com.signal.domain.repository

import com.signal.domain.entity.DiagnosisEntity
import com.signal.domain.entity.DiagnosisHistoryEntity

interface DiagnosisRepository {
    suspend fun getDiagnosis(): Result<List<DiagnosisEntity>>
    suspend fun setDiagnosis(diagnosisEntity: DiagnosisEntity)
    fun saveLastDiagnosisDate(
        date: String,
        accountId: String,
    )

    fun getLastDiagnosisDate(): Result<String>

    suspend fun getDiagnosisHistories(userId: String): List<DiagnosisHistoryEntity>

    suspend fun setDiagnosisHistory(diagnosisHistoryEntity: DiagnosisHistoryEntity)

    suspend fun addDiagnosisHistory(diagnosisHistoryEntity: DiagnosisHistoryEntity)
}