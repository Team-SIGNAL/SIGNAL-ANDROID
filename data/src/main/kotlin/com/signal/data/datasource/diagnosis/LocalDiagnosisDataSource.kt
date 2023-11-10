package com.signal.data.datasource.diagnosis

import com.signal.data.model.diagnosis.DiagnosisModel

interface LocalDiagnosisDataSource {
    suspend fun getDiagnosis(): List<DiagnosisModel>

    suspend fun setDiagnosis(diagnosisModel: DiagnosisModel)
}