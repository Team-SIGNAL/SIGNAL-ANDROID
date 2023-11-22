package com.signal.data.datasource.recommend

import com.signal.data.model.recommend.FetchRecommendsResponse
import com.signal.domain.enums.Category

interface RecommendDataSource {
    suspend fun fetchRecommends(category: Category): FetchRecommendsResponse
}