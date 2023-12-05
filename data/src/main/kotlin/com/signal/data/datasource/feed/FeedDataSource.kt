package com.signal.data.datasource.feed

import com.signal.data.model.feed.request.CreateCommentRequest
import com.signal.data.model.feed.request.CreatePostRequest
import com.signal.data.model.feed.response.FetchCommentsResponse
import com.signal.data.model.feed.response.FetchPostDetailsResponse
import com.signal.data.model.feed.response.FetchPostsResponse
import com.signal.domain.enums.Tag
import java.util.UUID

interface FeedDataSource {
    suspend fun fetchPosts(
        tag: Tag,
        page: Long,
        size: Long,
    ): FetchPostsResponse

    suspend fun createPost(createPostRequest: CreatePostRequest)

    suspend fun fetchPostDetails(feedId: UUID): FetchPostDetailsResponse

    suspend fun fetchComments(feedId: UUID): FetchCommentsResponse

    suspend fun createComment(
        feedId: UUID,
        createCommentRequest: CreateCommentRequest,
    )

    suspend fun deletePost(feedId: UUID)

    suspend fun editPost(
        feedId: UUID,
        createPostRequest: CreatePostRequest,
    )

    suspend fun reportFeed(feedId: UUID)
}
