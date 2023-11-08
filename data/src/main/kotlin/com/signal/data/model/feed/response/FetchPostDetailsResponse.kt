package com.signal.data.model.feed.response

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.PostDetailsEntity

data class FetchPostDetailsResponse(
    @SerializedName("img") val imageUrl: String?,
    @SerializedName("title") val title: String,
    @SerializedName("date") val date: String,
    @SerializedName("writer") val writer: String,
    @SerializedName("content") val content: String,
    @SerializedName("profile") val profile: String?,
    @SerializedName("isMine") val isMine: Boolean,
)

fun FetchPostDetailsResponse.toEntity() = PostDetailsEntity(
    imageUrl = this.imageUrl,
    title = this.title,
    date = this.date,
    writer = this.writer,
    content = this.content,
    profile = this.profile,
    isMine = this.isMine,
)
