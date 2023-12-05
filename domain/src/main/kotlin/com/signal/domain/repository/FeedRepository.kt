package com.signal.domain.repository

import com.signal.domain.entity.PostCommentsEntity
import com.signal.domain.entity.PostDetailsEntity
import com.signal.domain.entity.PostsEntity
import com.signal.domain.enums.Tag
import java.util.UUID

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
    ): Result<Unit>

    suspend fun fetchPostDetails(feedId: UUID): Result<PostDetailsEntity>

    suspend fun fetchComments(feedId: UUID): Result<PostCommentsEntity>

    suspend fun createComment(
        feedId: UUID,
        content: String,
    ): Result<Unit>

    suspend fun deletePost(feedId: UUID): Result<Unit>

    suspend fun editPost(
        feedId: UUID,
        title: String,
        content: String,
        image: String?,
    ): Result<Unit>

    suspend fun reportFeed(feedId: UUID): Result<Unit>
}
