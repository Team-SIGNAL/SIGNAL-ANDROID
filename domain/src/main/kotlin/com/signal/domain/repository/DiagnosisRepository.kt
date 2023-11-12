package com.signal.domain.repository

import com.signal.domain.entity.DiagnosisEntity

interface DiagnosisRepository {
    suspend fun getDiagnosis(): Result<List<DiagnosisEntity>>
    suspend fun setDiagnosis(diagnosisEntity: DiagnosisEntity)
    fun saveLastDiagnosisDate(date: String)
}