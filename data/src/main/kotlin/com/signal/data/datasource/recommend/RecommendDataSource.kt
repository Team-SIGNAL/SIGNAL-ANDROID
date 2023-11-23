package com.signal.data.datasource.recommend

import com.signal.data.model.recommend.FetchRecommendDetailsResponse
import com.signal.data.model.recommend.FetchRecommendsResponse
import com.signal.domain.enums.Category
import java.util.UUID

interface RecommendDataSource {
    suspend fun fetchRecommends(category: Category): FetchRecommendsResponse

    suspend fun fetchRecommendDetails(recommendId: UUID): FetchRecommendDetailsResponse
}