package com.signal.domain.entity

data class DiagnosisHistoryEntity(
    val id: Long,
    val score: Long,
    val userId: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val week: Int,
)
