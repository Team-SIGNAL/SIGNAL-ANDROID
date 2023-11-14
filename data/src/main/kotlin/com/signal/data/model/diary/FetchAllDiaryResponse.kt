package com.signal.data.model.diary

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.AllDiaryEntity
import com.signal.domain.enums.Emotion

data class FetchAllDiaryResponse(
    @SerializedName("diary_list") val diaryList: List<Diaries>
) {
    data class Diaries(
        @SerializedName("id") val id: Long,
        @SerializedName("title") val title: String,
        @SerializedName("content") val content: String,
        @SerializedName("image") val image: String,
        @SerializedName("emotion") val emotion: Emotion,
    )
}

fun FetchAllDiaryResponse.toEntity() = AllDiaryEntity(
    allDiaryEntity = this.diaryList.map { it.toEntity() },
)

private fun FetchAllDiaryResponse.Diaries.toEntity() = AllDiaryEntity.AllDiaryEntity(
    id = this.id,
    title = this.title,
    content = this.content,
    image = this.image,
    emotion = this.emotion,
)