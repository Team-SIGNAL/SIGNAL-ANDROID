package com.signal.domain.entity

import java.time.LocalDateTime

data class PostCommentsEntity(
    val comments: List<CommentEntity>,
) {
    data class CommentEntity(
        val writer: String,
        val content: String,
        val isMine: Boolean,
        val dateTime: LocalDateTime,
    )
}
