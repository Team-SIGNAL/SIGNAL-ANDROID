package com.signal.data.datasource.feed

import com.signal.data.model.feed.FetchPostsResponse
import com.signal.domain.enums.Tag

interface FeedDataSource {
    suspend fun fetchPosts(
        tag: Tag,
        pageNum: Long,
    ): FetchPostsResponse
}
