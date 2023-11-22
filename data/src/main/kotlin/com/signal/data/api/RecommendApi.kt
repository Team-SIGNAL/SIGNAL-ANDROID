package com.signal.data.api

import com.signal.data.model.recommend.FetchRecommendsResponse
import com.signal.domain.enums.Category
import retrofit2.http.GET
import retrofit2.http.Query

interface RecommendApi {
    @GET(SignalUrl.Recommend.FetchRecommends)
    suspend fun fetchRecommends(
        @Query("category") category: Category,
    ): FetchRecommendsResponse
}