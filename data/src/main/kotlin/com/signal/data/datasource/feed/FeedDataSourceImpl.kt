package com.signal.data.datasource.feed

import com.signal.data.api.FeedApi
import com.signal.data.model.feed.request.CreateCommentRequest
import com.signal.data.model.feed.request.CreatePostRequest
import com.signal.data.model.feed.response.FetchCommentsResponse
import com.signal.data.model.feed.response.FetchPostDetailsResponse
import com.signal.data.model.feed.response.FetchPostsResponse
import com.signal.data.util.ExceptionHandler
import com.signal.domain.enums.Tag

class FeedDataSourceImpl(
    private val feedApi: FeedApi,
) : FeedDataSource {
    override suspend fun fetchPosts(
        tag: Tag,
        page: Long,
        size: Long,
    ): FetchPostsResponse = ExceptionHandler<FetchPostsResponse>().httpRequest {
        feedApi.fetchPosts(
            tag = tag,
            page = page,
            size = size,
        )
    }.sendRequest()

    override suspend fun createPost(createPostRequest: CreatePostRequest) =
        ExceptionHandler<Unit>().httpRequest {
            feedApi.createPost(createPostRequest = createPostRequest)
        }.sendRequest()

    override suspend fun fetchPostDetails(feedId: Long) =
        ExceptionHandler<FetchPostDetailsResponse>().httpRequest {
            feedApi.fetchPostDetails(feedId = feedId)
        }.sendRequest()

    override suspend fun fetchPostComments(feedId: Long) =
        ExceptionHandler<FetchCommentsResponse>().httpRequest {
            feedApi.fetchPostComments(feedId = feedId)
        }.sendRequest()

    override suspend fun createComment(
        feedId: Long,
        createCommentRequest: CreateCommentRequest,
    ) = ExceptionHandler<Unit>().httpRequest {
        feedApi.createComment(
            feedId = feedId,
            createCommentRequest = createCommentRequest,
        )
    }.sendRequest()

    override suspend fun deletePost(feedId: Long) = ExceptionHandler<Unit>().httpRequest {
        feedApi.deletePost(feedId = feedId)
    }.sendRequest()
}
