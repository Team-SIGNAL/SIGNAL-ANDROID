package com.signal.domain.entity

data class RecommendDetailsEntity(
    val title: String,
    val image: String,
    val content: String,
    val link: String,
    val name: String,
    val profile: String?,
    val createDate: String,
)