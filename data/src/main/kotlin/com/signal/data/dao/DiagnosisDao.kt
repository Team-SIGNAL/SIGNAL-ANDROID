package com.signal.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.signal.data.model.diagnosis.DiagnosisModel

@Dao
interface DiagnosisDao {
    @Query("select * from diagnosis")
    fun getDiagnosis(): List<DiagnosisModel>

    @Update
    fun setDiagnosis(diagnosisModel: DiagnosisModel)

    @Insert
    fun addDiagnosis(diagnosis: List<DiagnosisModel>)
}
