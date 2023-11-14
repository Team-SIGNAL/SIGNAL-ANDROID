package com.signal.data.model.feed.request

import com.google.gson.annotations.SerializedName
import com.signal.domain.enums.Tag

data class CreatePostRequest(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("image") val image: String?,
    @SerializedName("tag") val tag: Tag,
)
