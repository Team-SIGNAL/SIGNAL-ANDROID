package com.signal.domain.entity

data class DiagnosisEntity(
    val id: Long,
    val question: String,
    val score: Long?,
)
