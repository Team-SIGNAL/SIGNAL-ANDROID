package com.signal.data.model.feed.request

import com.google.gson.annotations.SerializedName

data class CreatePostRequest(
    @SerializedName("content") val content: String,
)
