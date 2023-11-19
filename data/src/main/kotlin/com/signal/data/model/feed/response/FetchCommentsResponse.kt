package com.signal.data.model.feed.response

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.PostCommentsEntity
import java.time.LocalDateTime

data class FetchCommentsResponse(
    @SerializedName("comment_list") val comments: List<Comment>,
) {
    data class Comment(
        @SerializedName("content") val content: String,
        @SerializedName("create_date_time") val dateTime: String,
        @SerializedName("name") val name: String,
        @SerializedName("profile") val profile: String?,
    )
}

fun FetchCommentsResponse.toEntity() = PostCommentsEntity(
    comments = this.comments.map { it.toEntity() },
)

fun FetchCommentsResponse.Comment.toEntity() = PostCommentsEntity.CommentEntity(
    content = this.content,
    dateTime = this.dateTime,
    name = this.name,
    profile = this.profile,
)
