package com.signal.data.model.diary

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.FetchDayDiaryEntity
import com.signal.domain.enums.Emotion

data class FetchDayDiaryResponse(
    @SerializedName("feed_list") val feedList: List<Diaries>,
) {
    data class Diaries(
        @SerializedName("id") val id: Long,
        @SerializedName("title") val title: String,
        @SerializedName("content") val content: String,
        @SerializedName("image") val image: String,
        @SerializedName("emotion") val emotion: Emotion,
    )
}

fun FetchDayDiaryResponse.toEntity() = FetchDayDiaryEntity(
    dayDiaryEntity = this.feedList.map { it.toEntity() },
)

private fun FetchDayDiaryResponse.Diaries.toEntity() = FetchDayDiaryEntity.DayDiaryEntity(
    id = this.id,
    title = this.title,
    content = this.content,
    image = this.image,
    emotion = this.emotion,
)
