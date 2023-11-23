package com.signal.data.api

import com.signal.data.model.recommend.FetchRecommendDetailsResponse
import com.signal.data.model.recommend.FetchRecommendsResponse
import com.signal.domain.enums.Category
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface RecommendApi {
    @GET(SignalUrl.Recommend.FetchRecommends)
    suspend fun fetchRecommends(
        @Query("category") category: Category,
    ): FetchRecommendsResponse

    @GET(SignalUrl.Recommend.RecommendId)
    suspend fun fetchRecommendDetails(
        @Path("recommend_id") recommendId: UUID,
    ): FetchRecommendDetailsResponse
}