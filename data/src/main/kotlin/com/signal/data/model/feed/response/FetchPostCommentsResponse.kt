package com.signal.data.model.feed.response

import com.google.gson.annotations.SerializedName
import com.signal.domain.entity.PostCommentsEntity
import java.time.LocalDateTime

data class FetchPostCommentsResponse(
    @SerializedName("comment") val comments: List<Comment>,
) {
    data class Comment(
        @SerializedName("writer") val writer: String,
        @SerializedName("content") val content: String,
        @SerializedName("is_mine") val isMine: Boolean,
        @SerializedName("date_time") val dateTime: LocalDateTime,
    )
}

fun FetchPostCommentsResponse.toEntity() = PostCommentsEntity(
    comments = this.comments.map { it.toEntity() },
)

fun FetchPostCommentsResponse.Comment.toEntity() = PostCommentsEntity.CommentEntity(
    writer = this.writer,
    content = this.content,
    isMine = this.isMine,
    dateTime = this.dateTime,
)
