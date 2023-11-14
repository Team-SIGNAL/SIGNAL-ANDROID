package com.signal.domain.repository

import com.signal.domain.entity.PostsEntity
import com.signal.domain.entity.PostCommentsEntity
import com.signal.domain.entity.PostDetailsEntity
import com.signal.domain.enums.Tag

interface FeedRepository {
    suspend fun fetchPosts(
        tag: Tag,
        page: Long,
        size: Long,
    ): PostsEntity

    suspend fun createPost(
        title: String,
        content: String,
        image: String?,
        tag: Tag,
    ): Result<Unit>

    suspend fun fetchPostDetails(feedId: Long): Result<PostDetailsEntity>

    suspend fun fetchPostComments(feedId: Long): Result<PostCommentsEntity>

    suspend fun createComment(
        feedId: Long,
        content: String,
    ): Result<Unit>
}
