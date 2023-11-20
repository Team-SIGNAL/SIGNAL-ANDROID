package com.signal.domain.entity

import com.signal.domain.enums.Emotion
import java.util.UUID

data class DiariesEntity (
    val diaryEntity: List<DiaryEntity>
) {
    data class DiaryEntity(
        val id: UUID,
        val title: String,
        val content: String,
        val image: String?,
        val emotion: Emotion,
    )
}