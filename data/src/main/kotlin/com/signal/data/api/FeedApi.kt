package com.signal.data.api

import com.signal.data.model.feed.request.PostRequest
import com.signal.data.model.feed.response.FetchPostCommentsResponse
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

    @POST(SignalUrl.Feed.CreatePost)
    suspend fun createPost(
        @Body postRequest: PostRequest,
    )

    @GET(SignalUrl.Feed.Details)
    suspend fun fetchPostDetails(
        @Path("feed_id") feedId: Long,
    ): FetchPostDetailsResponse

    @GET(SignalUrl.Feed.Comment)
    suspend fun fetchPostComments(
        @Path("feed_id") feedId: Long,
    ): FetchPostCommentsResponse
}
