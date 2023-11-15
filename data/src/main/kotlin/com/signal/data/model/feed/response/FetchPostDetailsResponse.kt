package com.signal.data.model.feed.response

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.PostDetailsEntity

data class FetchPostDetailsResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("image") val image: String?,
    @SerializedName("title") val title: String,
    @SerializedName("create_date") val date: String,
    @SerializedName("writer") val writer: String,
    @SerializedName("content") val content: String,
    @SerializedName("profile") val profile: String?,
    @SerializedName("mine") val mine: Boolean,
)

fun FetchPostDetailsResponse.toEntity() = PostDetailsEntity(
    id = this.id,
    image = this.image,
    title = this.title,
    date = this.date,
    writer = this.writer,
    content = this.content,
    profile = this.profile,
    isMine = this.mine,
)
