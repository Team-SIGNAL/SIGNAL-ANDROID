package com.signal.domain.repository

import com.signal.domain.PostsEntity
import com.signal.domain.entity.PostDetailsEntity
import com.signal.domain.enums.Tag

interface FeedRepository {
    suspend fun fetchPosts(
        tag: Tag,
        pageNum: Long,
    ): PostsEntity

    suspend fun post(
        title: String,
        content: String,
    ): Result<Unit>

    suspend fun fetchPostDetails(feedId: Long): Result<PostDetailsEntity>
}
