package com.signal.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.signal.data.model.diagnosis.DiagnosisEntity

@Dao
interface DiagnosisDao {
    @Query("select * from diagnosis")
    fun getDiagnosis(): List<DiagnosisEntity>

    @Update
    fun setDiagnosis(diagnosisEntity: DiagnosisEntity)

    @Insert
    fun addDiagnosis(diagnosis: List<DiagnosisEntity>)
}
