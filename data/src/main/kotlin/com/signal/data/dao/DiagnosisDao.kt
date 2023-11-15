package com.signal.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.signal.data.model.diagnosis.DiagnosisHistoryModel
import com.signal.data.model.diagnosis.DiagnosisModel

@Dao
interface DiagnosisDao {
    @Query("select * from diagnosis")
    fun getDiagnosis(): List<DiagnosisModel>

    @Query("select * from diagnosisHistory")
    fun getDiagnosisHistories(): List<DiagnosisHistoryModel>

    @Update
    fun setDiagnosis(diagnosisModel: DiagnosisModel)

    @Update
    fun setDiagnosisHistory(diagnosisHistory: DiagnosisHistoryModel)

    @Insert
    fun addDiagnosis(diagnosis: List<DiagnosisModel>)

    @Insert
    fun addDiagnosisHistory(diagnosisHistory: DiagnosisHistoryModel)
}
