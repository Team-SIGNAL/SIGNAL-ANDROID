package com.signal.data.api

import com.signal.data.model.feed.request.PostRequest
import com.signal.data.model.feed.response.FetchPostDetailsResponse
import com.signal.data.model.feed.response.FetchPostsResponse
import com.signal.domain.enums.Tag
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FeedApi {
    @GET(SignalUrl.Feed.List)
    suspend fun fetchPosts(
        @Query("tag") tag: Tag,
        @Query("pagenum") pageNum: Long,
    ): FetchPostsResponse

    @POST(SignalUrl.Feed.Post)
    suspend fun post(
        @Body postRequest: PostRequest,
    )

    @GET(SignalUrl.Feed.Post)
    suspend fun fetchPostDetails(
        @Path("feed_id") feedId: Long,
    ): FetchPostDetailsResponse
}
