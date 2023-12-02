package com.signal.data.datasource.diagnosis

import com.signal.data.model.diagnosis.DiagnosisHistoryModel
import com.signal.data.model.diagnosis.DiagnosisModel

interface LocalDiagnosisDataSource {
    suspend fun getDiagnosis(): List<DiagnosisModel>

    suspend fun getDiagnosisHistories(userId: String): List<DiagnosisHistoryModel>

    suspend fun getDiagnosisCount(): Long

    suspend fun setDiagnosis(diagnosisModel: DiagnosisModel)

    suspend fun setDiagnosisHistory(diagnosisHistoryModel: DiagnosisHistoryModel)

    fun saveLastDiagnosisDate(
        date: String,
        accountId: String,
    )

    fun getLastDiagnosisDate(): String

    fun addDiagnosisHistory(diagnosisHistoryModel: DiagnosisHistoryModel)
}
