package com.signal.data.model.diagnosis

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("diagnosis")
data class DiagnosisEntity(
    @PrimaryKey val id: Long,
    val question: String,
    val score: Long,
)
