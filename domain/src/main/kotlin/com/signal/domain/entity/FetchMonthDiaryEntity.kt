package com.signal.domain.entity

import java.time.LocalDateTime

data class FetchMonthDiaryEntity(
    val monthDiaryEntity: List<MonthDiaryEntity>,
) {
    data class MonthDiaryEntity(
        val id: Long,
        val date: LocalDateTime,
    )
}