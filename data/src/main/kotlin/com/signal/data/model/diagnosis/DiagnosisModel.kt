package com.signal.data.model.diagnosis

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.signal.domain.entity.DiagnosisEntity

@Entity("diagnosis")
data class DiagnosisModel(
    @PrimaryKey val id: Long,
    val question: String,
    val score: Long?,
)

fun DiagnosisModel.toEntity() = DiagnosisEntity(
    id = this.id,
    question = this.question,
    score = this.score,
)

fun DiagnosisEntity.toModel() = DiagnosisModel(
    id = this.id,
    question = this.question,
    score = this.score,
)
