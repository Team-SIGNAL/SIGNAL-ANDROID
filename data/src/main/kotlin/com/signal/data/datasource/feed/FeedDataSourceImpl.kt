package com.signal.data.datasource.feed

import com.signal.data.api.FeedApi
import com.signal.data.model.feed.FetchPostsResponse
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
}
