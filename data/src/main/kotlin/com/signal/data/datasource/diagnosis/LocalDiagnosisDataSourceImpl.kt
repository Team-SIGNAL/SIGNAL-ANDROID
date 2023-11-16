package com.signal.data.datasource.diagnosis

import com.signal.data.database.SignalDatabase
import com.signal.data.datasource.user.local.Keys
import com.signal.data.model.diagnosis.DiagnosisHistoryModel
import com.signal.data.model.diagnosis.DiagnosisModel
import com.signal.data.util.PreferenceManager

class LocalDiagnosisDataSourceImpl(
    database: SignalDatabase,
    private val preferenceManager: PreferenceManager,
) : LocalDiagnosisDataSource {
    private val diagnosisDao = database.getDiagnosisDao()

    override suspend fun getDiagnosis() = diagnosisDao.getDiagnosis()

    override suspend fun getDiagnosisHistories(userId: String) =
        diagnosisDao.getDiagnosisHistories(userId = userId)

    override suspend fun setDiagnosis(diagnosisModel: DiagnosisModel) {
        diagnosisDao.setDiagnosis(diagnosisModel)
    }

    override suspend fun setDiagnosisHistory(diagnosisHistoryModel: DiagnosisHistoryModel) {
        diagnosisDao.setDiagnosisHistory(diagnosisHistoryModel)
    }

    override fun saveLastDiagnosisDate(
        date: String,
        accountId: String,
    ) {
        preferenceManager.getSharedPreferenceEditor()
            .putString(Keys.LAST_DIAGNOSIS_DATE + accountId, date)
            .apply()
    }

    override fun getLastDiagnosisDate(): String = with(preferenceManager.getSharedPreference()) {
        return getString(Keys.LAST_DIAGNOSIS_DATE + getString(Keys.ACCOUNT_ID, ""), "") ?: ""
    }

    override fun addDiagnosisHistory(diagnosisHistoryModel: DiagnosisHistoryModel) {
        diagnosisDao.addDiagnosisHistory(diagnosisHistoryModel)
    }
}
