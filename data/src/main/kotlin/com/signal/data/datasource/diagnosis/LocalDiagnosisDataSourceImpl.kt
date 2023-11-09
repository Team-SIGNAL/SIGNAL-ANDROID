package com.signal.data.datasource.diagnosis

import com.signal.data.database.SignalDatabase
import com.signal.data.model.diagnosis.DiagnosisModel

class LocalDiagnosisDataSourceImpl(
    private val database: SignalDatabase,
) : LocalDiagnosisDataSource {
    private val diagnosisDao = database.getDiagnosisDao()

    override suspend fun getDiagnosis() = diagnosisDao.getDiagnosis()

    override suspend fun setDiagnosis(diagnosisModel: DiagnosisModel) {
        diagnosisDao.setDiagnosis(diagnosisModel)
    }
}
