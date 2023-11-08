package com.signal.domain.entity

data class PostDetailsEntity(
    val imageUrl: String?,
    val title: String,
    val date: String,
    val writer: String,
    val content: String,
    val profile: String?,
    val isMine: Boolean,
)
