package com.signal.data.datasource.feed

import com.signal.data.model.feed.request.CreatePostRequest
import com.signal.data.model.feed.request.PostRequest
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

    suspend fun createPost(postRequest: PostRequest)

    suspend fun fetchPostDetails(feedId: Long): FetchPostDetailsResponse

    suspend fun fetchPostComments(feedId: Long): FetchCommentsResponse

    suspend fun createComment(
        feedId: Long,
        createPostRequest: CreatePostRequest,
    )
}
