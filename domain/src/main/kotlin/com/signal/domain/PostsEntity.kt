package com.signal.domain

data class PostsEntity(
    val postEntities: List<PostEntity>,
    val pageTotal: Long,
) {
    data class PostEntity(
        val id: Long,
        val title: String,
        val image: String?,
        val name: String,
        val date: String,
    )
}
