package com.signal.data.model.feed.response

import com.google.gson.annotations.SerializedName
import com.signal.domain.PostsEntity

data class FetchPostsResponse(
    @SerializedName("feed_list") val posts: List<Post>,
    @SerializedName("page_total") val pageTotal: Long,
) {
    data class Post(
        @SerializedName("id") val id: Long,
        @SerializedName("title") val title: String,
        @SerializedName("image") val image: String?,
        @SerializedName("name") val name: String,
        @SerializedName("create_date") val date: String,
    )
}

fun FetchPostsResponse.toEntity() = PostsEntity(
    postEntities = this.posts.map { it.toEntity() },
    pageTotal = this.pageTotal
)

private fun FetchPostsResponse.Post.toEntity() = PostsEntity.PostEntity(
    id = this.id,
    title = this.title,
    image = this.image,
    name = this.name,
    date = this.date,
)
