package com.signal.signal_android

import android.content.Context
import androidx.room.Room
import com.signal.data.database.SignalDatabase
import com.signal.data.model.diagnosis.DiagnosisEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DBInitializer(private val context: Context) {
    private val database: SignalDatabase by lazy {
        Room.databaseBuilder(
            context = context,
            klass = SignalDatabase::class.java,
            name = "signal-database",
        ).build()
    }

    private val diagnosisDao by lazy {
        database.getDiagnosisDao()
    }

    init {
        initQuestions()
    }

    private val diagnosisQuestions = mutableListOf<DiagnosisEntity>()

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

    private fun initQuestions() {
        CoroutineScope(Dispatchers.IO).launch {
            if (diagnosisDao.getDiagnosis().isEmpty()) {
                diagnosisResources.forEachIndexed { index, i ->
                    diagnosisQuestions.add(
                        DiagnosisEntity(
                            id = index.toLong(),
                            question = context.getString(i),
                            score = 0,
                        ),
                    )
                }
                diagnosisDao.addDiagnosis(diagnosisQuestions)
            }
        }
    }
}
