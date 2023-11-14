package com.signal.domain.entity

import com.signal.domain.enums.Emotion

data class DayDiaryEntity(
    val dayDiaryEntity: List<DayDiaryEntity>,
) {
    data class DayDiaryEntity(
        val id: Long,
        val title: String,
        val content: String,
        val image: String,
        val emotion: Emotion,
    )

}
