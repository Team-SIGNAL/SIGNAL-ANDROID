package com.signal.data.api

import com.signal.data.model.feed.FetchPostsResponse
import com.signal.domain.enums.Tag
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedApi {
    @GET(SignalUrl.Feed.List)
    suspend fun fetchPosts(
        @Query("tag") tag: Tag,
        @Query("pagenum") pageNum: Long,
    ): FetchPostsResponse
}
