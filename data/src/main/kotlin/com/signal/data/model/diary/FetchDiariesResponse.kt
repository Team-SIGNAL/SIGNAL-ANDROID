package com.signal.data.model.diary

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.DiariesEntity
import com.signal.domain.enums.Emotion

data class FetchDiariesResponse(
    @SerializedName("diary_list") val diaries: List<Diaries>
) {
    data class Diaries(
        @SerializedName("id") val id: Long,
        @SerializedName("title") val title: String,
        @SerializedName("content") val content: String,
        @SerializedName("image") val image: String?,
        @SerializedName("emotion") val emotion: Emotion,
    )
}

fun FetchDiariesResponse.toEntity() = DiariesEntity(
    diaryEntity = this.diaries.map { it.toEntity() },
)

private fun FetchDiariesResponse.Diaries.toEntity() = DiariesEntity.DiaryEntity(
    id = this.id,
    title = this.title,
    content = this.content,
    image = this.image,
    emotion = this.emotion,
)