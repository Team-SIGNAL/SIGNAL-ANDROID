package com.signal.data.model.diagnosis

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.signal.domain.entity.DiagnosisHistoryEntity
import java.time.LocalDate
import java.util.UUID

@Entity("diagnosisHistory")
data class DiagnosisHistoryModel(
    @PrimaryKey val id: Long,
    val score: Long,
    val userId: String,
    val date: String,
)

fun DiagnosisHistoryModel.toEntity() = DiagnosisHistoryEntity(
    id = this.id,
    score = this.score,
    userId = this.userId,
    date = this.date,
)

fun DiagnosisHistoryEntity.toModel() = DiagnosisHistoryModel(
    id = this.id,
    score = this.score,
    userId = this.userId,
    date = this.date,
)