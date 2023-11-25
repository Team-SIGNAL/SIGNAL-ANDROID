package com.signal.domain.entity

import java.util.UUID

data class RecommendsEntity(
    val recommends: List<Recommend>,
) {
    data class Recommend(
        val id: UUID,
        val title: String,
        val content: String,
        val image: String,
        val link: String?,
    )
}