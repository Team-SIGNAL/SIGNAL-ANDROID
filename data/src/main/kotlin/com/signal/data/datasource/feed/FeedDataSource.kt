package com.signal.data.datasource.feed

import com.signal.data.model.feed.request.PostRequest
import com.signal.data.model.feed.response.FetchPostsResponse
import com.signal.domain.enums.Tag

interface FeedDataSource {
    suspend fun fetchPosts(
        tag: Tag,
        pageNum: Long,
    ): FetchPostsResponse

    suspend fun post(postRequest: PostRequest)
}
