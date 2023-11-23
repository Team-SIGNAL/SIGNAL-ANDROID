package com.signal.data.model.recommend

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.RecommendDetailsEntity

data class FetchRecommendDetailsResponse(
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
    @SerializedName("content") val content: String,
    @SerializedName("link") val link: String,
    @SerializedName("name") val name: String,
    @SerializedName("profile") val profile: String,
    @SerializedName("create_date") val createDate: String,
)

fun FetchRecommendDetailsResponse.toEntity() = RecommendDetailsEntity(
    title = this.title,
    image = this.image,
    content = this.content,
    link = this.link,
    name = this.name,
    profile = this.profile,
    createDate = this.createDate,
)