package com.signal.domain.entity

import java.util.UUID

data class PostsEntity(
    val postEntities: List<PostEntity>,
    val pageTotal: Long,
) {
    data class PostEntity(
        val id: UUID,
        val title: String,
        val image: String?,
        val name: String,
        val date: String,
        val isMine: Boolean,
    )
}
