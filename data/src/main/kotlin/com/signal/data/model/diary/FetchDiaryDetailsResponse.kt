package com.signal.data.model.diary

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.DiaryDetailsEntity
import com.signal.domain.enums.Emotion

data class FetchDiaryDetailsResponse(
    @SerializedName("create_date") val date: String,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("emotion") val emotion: Emotion,
    @SerializedName("image") val image: String?,
)

fun FetchDiaryDetailsResponse.toEntity() = DiaryDetailsEntity(
    date = this.date,
    title = this.title,
    content = this.content,
    emotion = this.emotion,
    image = this.image,
)