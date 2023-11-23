package com.signal.domain.repository

import com.signal.domain.entity.RecommendsEntity
import com.signal.domain.enums.Category

interface RecommendRepository {
    suspend fun fetchRecommends(category: Category): Result<RecommendsEntity>
}