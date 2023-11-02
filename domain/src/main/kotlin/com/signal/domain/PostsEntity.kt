package com.signal.domain

data class PostsEntity(
    val postEntities: List<PostEntity>,
) {
    data class PostEntity(
        val id: Long,
        val img: String,
        val title: String,
        val date: String,
        val user: String,
    )
}
