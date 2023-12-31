package com.signal.data.repository

import com.signal.data.datasource.feed.FeedDataSource
import com.signal.data.model.feed.request.CreateCommentRequest
import com.signal.data.model.feed.request.CreatePostRequest
import com.signal.data.model.feed.response.toEntity
import com.signal.domain.entity.PostsEntity
import com.signal.domain.enums.Tag
import com.signal.domain.repository.FeedRepository
import java.util.UUID

class FeedRepositoryImpl(
    private val feedDataSource: FeedDataSource,
) : FeedRepository {
    override suspend fun fetchPosts(
        tag: Tag,
        page: Long,
        size: Long,
    ): PostsEntity = feedDataSource.fetchPosts(
        tag = tag,
        page = page,
        size = size,
    ).toEntity()

    override suspend fun createPost(
        title: String,
        content: String,
        image: String?,
    ) = runCatching {
        feedDataSource.createPost(
            CreatePostRequest(
                title = title,
                content = content,
                image = image,
            ),
        )
    }

    override suspend fun fetchPostDetails(feedId: UUID) = runCatching {
        feedDataSource.fetchPostDetails(feedId = feedId).toEntity()
    }

    override suspend fun fetchComments(feedId: UUID) = runCatching {
        feedDataSource.fetchComments(feedId = feedId).toEntity()
    }

    override suspend fun createComment(
        feedId: UUID,
        content: String,
    ) = kotlin.runCatching {
        feedDataSource.createComment(
            feedId = feedId,
            createCommentRequest = CreateCommentRequest(content = content),
        )
    }

    override suspend fun deletePost(feedId: UUID) = kotlin.runCatching {
        feedDataSource.deletePost(feedId = feedId)
    }

    override suspend fun editPost(
        feedId: UUID,
        title: String,
        content: String,
        image: String?,
    ) = kotlin.runCatching {
        feedDataSource.editPost(
            feedId = feedId,
            createPostRequest = CreatePostRequest(
                title = title,
                content = content,
                image = image,
            ),
        )
    }

    override suspend fun reportFeed(feedId: UUID) = kotlin.runCatching {
        feedDataSource.reportFeed(feedId = feedId)
    }
}
