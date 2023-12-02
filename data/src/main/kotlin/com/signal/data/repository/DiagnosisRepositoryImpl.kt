package com.signal.data.repository

import com.signal.data.datasource.diagnosis.LocalDiagnosisDataSource
import com.signal.data.model.diagnosis.toEntity
import com.signal.data.model.diagnosis.toModel
import com.signal.domain.entity.DiagnosisEntity
import com.signal.domain.entity.DiagnosisHistoryEntity
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

    override fun saveLastDiagnosisDate(
        date: String,
        accountId: String,
    ) {
        localDiagnosisDataSource.saveLastDiagnosisDate(
            date = date,
            accountId = accountId,
        )
    }

    override fun getLastDiagnosisDate() = runCatching {
        localDiagnosisDataSource.getLastDiagnosisDate()
    }

    override suspend fun getDiagnosisHistories(userId: String) =
        localDiagnosisDataSource.getDiagnosisHistories(userId = userId).map { it.toEntity() }

    override suspend fun getHistoryCount() = localDiagnosisDataSource.getDiagnosisCount()

    override suspend fun setDiagnosisHistory(diagnosisHistoryEntity: DiagnosisHistoryEntity) {
        localDiagnosisDataSource.setDiagnosisHistory(diagnosisHistoryEntity.toModel())
    }

    override suspend fun addDiagnosisHistory(diagnosisHistoryEntity: DiagnosisHistoryEntity) {
        localDiagnosisDataSource.addDiagnosisHistory(diagnosisHistoryEntity.toModel())
    }
}
