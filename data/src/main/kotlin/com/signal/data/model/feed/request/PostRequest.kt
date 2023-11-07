package com.signal.data.model.feed.request

import com.google.gson.annotations.SerializedName

data class PostRequest(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
)
