package com.signal.data.model.feed

import com.google.gson.annotations.SerializedName
import com.signal.domain.PostsEntity

data class FetchPostsResponse(
    @SerializedName("feed") val posts: List<Post>,
) {
    data class Post(
        @SerializedName("id") val id: Long,
        @SerializedName("img") val img: String,
        @SerializedName("title") val title: String,
        @SerializedName("date") val date: String,
        @SerializedName("user") val user: String,
    )
}

fun FetchPostsResponse.toEntity() = PostsEntity(
    postEntities = this.posts.map { it.toEntity() },
)

private fun FetchPostsResponse.Post.toEntity() = PostsEntity.PostEntity(
    id = this.id,
    img = this.img,
    title = this.title,
    date = this.date,
    user = this.user,
)
