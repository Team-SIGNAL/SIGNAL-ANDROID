package com.signal.data.model.feed.response

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.PostCommentsEntity
import java.time.LocalDateTime

data class FetchCommentsResponse(
    @SerializedName("comment") val comments: List<Comment>,
) {
    data class Comment(
        @SerializedName("writer") val writer: String,
        @SerializedName("content") val content: String,
        @SerializedName("create_date_time") val dateTime: LocalDateTime,
        @SerializedName("profile") val profile: String,
    )
}

fun FetchCommentsResponse.toEntity() = PostCommentsEntity(
    comments = this.comments.map { it.toEntity() },
)

fun FetchCommentsResponse.Comment.toEntity() = PostCommentsEntity.CommentEntity(
    writer = this.writer,
    content = this.content,
    dateTime = this.dateTime,
    profile = this.profile,
)
