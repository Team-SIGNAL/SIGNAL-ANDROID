package com.signal.data.model.feed.response

import com.google.gson.annotations.SerializedName

data class FetchPostDetailsResponse(
    @SerializedName("img") val imageUrl: String,
    @SerializedName("title") val title: String,
    @SerializedName("date") val date: String,
    @SerializedName("writer") val writer: String,
    @SerializedName("content") val content: String,
    @SerializedName("isMine") val isMine: Boolean,
)
