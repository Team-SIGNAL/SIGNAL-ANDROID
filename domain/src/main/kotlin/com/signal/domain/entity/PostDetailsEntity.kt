package com.signal.domain.entity

data class PostDetailsEntity(
    val id: Long,
    val image: String?,
    val title: String,
    val date: String,
    val writer: String,
    val content: String,
    val profile: String?,
    val isMine: Boolean,
)
