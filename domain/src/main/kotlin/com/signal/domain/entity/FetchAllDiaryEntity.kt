package com.signal.domain.entity

import com.signal.domain.enums.Emotion

data class FetchAllDiaryEntity(
    val allDiaryEntity: List<AllDiaryEntity>
) {
    data class AllDiaryEntity(
        val id: Long,
        val title: String,
        val content: String,
        val image: String,
        val emotion: Emotion,
    )

}