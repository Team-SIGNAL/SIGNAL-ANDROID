package com.signal.domain.repository

import com.signal.domain.entity.RecommendDetailsEntity
import com.signal.domain.entity.RecommendsEntity
import com.signal.domain.enums.Category
import java.util.UUID

interface RecommendRepository {
    suspend fun fetchRecommends(category: Category): Result<RecommendsEntity>

    suspend fun fetchRecommendDetails(recommendId: UUID): Result<RecommendDetailsEntity>
}