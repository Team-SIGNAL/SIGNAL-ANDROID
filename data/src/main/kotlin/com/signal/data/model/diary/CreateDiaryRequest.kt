package com.signal.data.model.diary

import com.google.gson.annotations.SerializedName
import com.signal.domain.enums.Emotion

data class CreateDiaryRequest(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("emotion") val emotion: Emotion,
    @SerializedName("date") val date: String,
    @SerializedName("image") val image: String?,
) {
}