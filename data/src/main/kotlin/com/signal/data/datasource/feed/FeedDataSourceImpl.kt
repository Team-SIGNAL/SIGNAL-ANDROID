package com.signal.data.datasource.feed

import com.signal.data.api.FeedApi
import com.signal.data.model.feed.request.PostRequest
import com.signal.data.model.feed.response.FetchPostsResponse
import com.signal.data.util.ExceptionHandler
import com.signal.domain.enums.Tag

class FeedDataSourceImpl(
    private val feedApi: FeedApi,
) : FeedDataSource {
    override suspend fun fetchPosts(
        tag: Tag,
        pageNum: Long,
    ): FetchPostsResponse = ExceptionHandler<FetchPostsResponse>().httpRequest {
        feedApi.fetchPosts(
            tag = tag,
            pageNum = pageNum,
        )
    }.sendRequest()

    override suspend fun post(postRequest: PostRequest) = ExceptionHandler<Unit>().httpRequest {
        feedApi.post(postRequest)
    }.sendRequest()
}
