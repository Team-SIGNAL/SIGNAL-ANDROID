package com.signal.domain.entity

data class DiagnosisHistoryEntity(
    val id: Long,
    val score: Long,
    val userId: String,
    val date: String,
)
