package com.signal.data.api

import com.signal.data.model.feed.request.CreateCommentRequest
import com.signal.data.model.feed.request.CreatePostRequest
import com.signal.data.model.feed.response.FetchCommentsResponse
import com.signal.data.model.feed.response.FetchPostDetailsResponse
import com.signal.data.model.feed.response.FetchPostsResponse
import com.signal.domain.enums.Tag
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface FeedApi {
    @GET(SignalUrl.Feed.List)
    suspend fun fetchPosts(
        @Query("tag") tag: Tag,
        @Query("page") page: Long,
        @Query("size") size: Long,
    ): FetchPostsResponse

    @POST(SignalUrl.Feed.CreatePost)
    suspend fun createPost(
        @Body createPostRequest: CreatePostRequest,
    )

    @GET(SignalUrl.Feed.Details)
    suspend fun fetchPostDetails(
        @Path("feed_id") feedId: UUID,
    ): FetchPostDetailsResponse

    @GET(SignalUrl.Feed.FetchComment)
    suspend fun fetchComments(
        @Path("feed_id") feedId: UUID,
    ): FetchCommentsResponse

    @POST(SignalUrl.Feed.CreateComment)
    suspend fun createComment(
        @Path("feed_id") feedId: UUID,
        @Body createCommentRequest: CreateCommentRequest,
    )

    @DELETE(SignalUrl.Feed.FeedId)
    suspend fun deletePost(
        @Path("feed_id") feedId: UUID,
    )

    @PATCH(SignalUrl.Feed.FeedId)
    suspend fun editPost(
        @Path("feed_id") feedId: UUID,
        @Body createPostRequest: CreatePostRequest,
    )

    @PATCH(SignalUrl.Feed.ReportFeed)
    suspend fun reportFeed(
        @Path("feed_id") feedId: UUID,
    )
}
