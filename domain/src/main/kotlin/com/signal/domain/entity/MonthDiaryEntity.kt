package com.signal.domain.entity

import java.time.LocalDateTime
import java.util.UUID

data class MonthDiaryEntity(
    val monthDiaryEntity: List<MonthDiaryEntity>,
) {
    data class MonthDiaryEntity(
        val id: UUID,
        val date: LocalDateTime,
    )
}