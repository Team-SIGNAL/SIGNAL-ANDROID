package com.signal.data.datasource.feed

import com.signal.data.model.feed.request.CreateCommentRequest
import com.signal.data.model.feed.request.CreatePostRequest
import com.signal.data.model.feed.response.FetchCommentsResponse
import com.signal.data.model.feed.response.FetchPostDetailsResponse
import com.signal.data.model.feed.response.FetchPostsResponse
import com.signal.domain.enums.Tag

interface FeedDataSource {
    suspend fun fetchPosts(
        tag: Tag,
        page: Long,
        size: Long,
    ): FetchPostsResponse

    suspend fun createPost(createPostRequest: CreatePostRequest)

    suspend fun fetchPostDetails(feedId: Long): FetchPostDetailsResponse

    suspend fun fetchComments(feedId: Long): FetchCommentsResponse

    suspend fun createComment(
        feedId: Long,
        createCommentRequest: CreateCommentRequest,
    )

    suspend fun deletePost(feedId: Long)

    suspend fun editPost(
        feedId: Long,
        createPostRequest: CreatePostRequest,
    )
}
