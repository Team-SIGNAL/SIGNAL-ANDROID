package com.signal.data.repository

import com.signal.data.datasource.diagnosis.LocalDiagnosisDataSource
import com.signal.data.model.diagnosis.toEntity
import com.signal.data.model.diagnosis.toModel
import com.signal.domain.entity.DiagnosisEntity
import com.signal.domain.repository.DiagnosisRepository

class DiagnosisRepositoryImpl(
    private val localDiagnosisDataSource: LocalDiagnosisDataSource,
) : DiagnosisRepository {
    override suspend fun getDiagnosis() = runCatching {
        localDiagnosisDataSource.getDiagnosis().map { it.toEntity() }
    }

    override suspend fun setDiagnosis(diagnosisEntity: DiagnosisEntity) {
        localDiagnosisDataSource.setDiagnosis(diagnosisEntity.toModel())
    }
}