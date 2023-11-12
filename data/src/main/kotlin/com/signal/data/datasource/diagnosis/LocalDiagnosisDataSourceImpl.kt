package com.signal.data.datasource.diagnosis

import com.signal.data.database.SignalDatabase
import com.signal.data.datasource.user.local.Keys
import com.signal.data.model.diagnosis.DiagnosisModel
import com.signal.data.util.PreferenceManager

class LocalDiagnosisDataSourceImpl(
    database: SignalDatabase,
    private val preferenceManager: PreferenceManager,
) : LocalDiagnosisDataSource {
    private val diagnosisDao = database.getDiagnosisDao()

    override suspend fun getDiagnosis() = diagnosisDao.getDiagnosis()

    override suspend fun setDiagnosis(diagnosisModel: DiagnosisModel) {
        diagnosisDao.setDiagnosis(diagnosisModel)
    }

    override fun saveLastDiagnosisDate(date: String) {
        preferenceManager.getSharedPreferenceEditor().putString(Keys.LAST_DIAGNOSIS_DATE, date)
    }
}
