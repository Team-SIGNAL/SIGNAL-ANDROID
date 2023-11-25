package com.signal.data.model.recommend

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.RecommendsEntity
import java.util.UUID

data class FetchRecommendsResponse(
    @SerializedName("recommend_list") val recommends: List<Recommend>,
) {
    data class Recommend(
        @SerializedName("id") val id: UUID,
        @SerializedName("title") val title: String,
        @SerializedName("content") val content: String,
        @SerializedName("image") val image: String,
        @SerializedName("link") val link: String?,
    )
}

fun FetchRecommendsResponse.toEntity() = RecommendsEntity(
    recommends = this.recommends.map { it.toEntity() },
)

fun FetchRecommendsResponse.Recommend.toEntity() = RecommendsEntity.Recommend(
    id = this.id,
    title = this.title,
    content = this.content,
    image = this.image,
    link = this.link,
)