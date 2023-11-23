package com.signal.domain.entity

import java.util.UUID

data class PostDetailsEntity(
    val id: UUID,
    val image: String?,
    val title: String,
    val date: String,
    val writer: String,
    val content: String,
    val profile: String?,
    val isMine: Boolean,
)
