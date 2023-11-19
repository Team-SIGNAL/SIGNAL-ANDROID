package com.signal.domain.entity

data class PostCommentsEntity(
    val comments: List<CommentEntity>,
) {
    data class CommentEntity(
        val content: String,
        val dateTime: String,
        val name: String,
        val profile: String?,
    )
}
