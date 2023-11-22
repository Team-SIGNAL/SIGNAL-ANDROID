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
    val year: Int,
    val month: Int,
    val day: Int,
    val week: Int,
)

fun DiagnosisHistoryModel.toEntity() = DiagnosisHistoryEntity(
    id = this.id,
    score = this.score,
    userId = this.userId,
    year = this.year,
    month = this.month,
    day = this.day,
    week = this.week,
)

fun DiagnosisHistoryEntity.toModel() = DiagnosisHistoryModel(
    id = this.id,
    score = this.score,
    userId = this.userId,
    year = this.year,
    month = this.month,
    day = this.day,
    week = this.week,
)