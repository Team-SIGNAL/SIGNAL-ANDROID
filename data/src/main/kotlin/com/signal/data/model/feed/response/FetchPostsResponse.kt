package com.signal.data.model.feed.response

import com.google.gson.annotations.SerializedName
import com.signal.domain.PostsEntity

data class FetchPostsResponse(
    @SerializedName("feed_list") val feeds: List<Feed>,
    @SerializedName("page_total") val pageTotal: Long,
) {
    data class Feed(
        @SerializedName("id") val id: Long,
        @SerializedName("title") val title: String,
        @SerializedName("image") val image: String?,
        @SerializedName("name") val name: String,
        @SerializedName("create_date") val date: String,
    )
}

fun FetchPostsResponse.toEntity() = PostsEntity(
    postEntities = this.feeds.map { it.toEntity() },
    pageTotal = this.pageTotal
)

private fun FetchPostsResponse.Feed.toEntity() = PostsEntity.PostEntity(
    id = this.id,
    title = this.title,
    image = this.image,
    name = this.name,
    date = this.date,
)
