package com.signal.domain.entity

import com.signal.domain.enums.Emotion

data class DiaryDetailsEntity(
    val date: String,
    val title: String,
    val content: String,
    val emotion: Emotion,
    val image: String?,
)
