package com.signal.signal_android

import android.content.Context
import com.signal.data.database.SignalDatabase
import com.signal.data.model.diagnosis.DiagnosisModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DBInitializer(
    private val context: Context,
    private val database: SignalDatabase,
) {
    private val diagnosisDao by lazy {
        database.getDiagnosisDao()
    }

    private val diagnosisQuestions = mutableListOf<DiagnosisModel>()

    private val diagnosisResources = listOf(
        R.string.diagnosis_1,
        R.string.diagnosis_2,
        R.string.diagnosis_3,
        R.string.diagnosis_4,
        R.string.diagnosis_5,
        R.string.diagnosis_6,
        R.string.diagnosis_7,
        R.string.diagnosis_8,
        R.string.diagnosis_9,
        R.string.diagnosis_10,
        R.string.diagnosis_11,
        R.string.diagnosis_12,
        R.string.diagnosis_13,
        R.string.diagnosis_14,
        R.string.diagnosis_15,
    )

    internal fun initQuestions() {
        CoroutineScope(Dispatchers.IO).launch {
            if (diagnosisDao.getDiagnosis().isEmpty()) {
                diagnosisResources.forEachIndexed { index, i ->
                    diagnosisQuestions.add(
                        DiagnosisModel(
                            id = index.toLong(),
                            question = context.getString(i),
                            score = null,
                        ),
                    )
                }
                diagnosisDao.addDiagnosis(diagnosisQuestions)
            }
        }
    }
}
