package com.signal.data.repository

import com.signal.data.datasource.recommend.RecommendDataSource
import com.signal.data.model.recommend.toEntity
import com.signal.domain.enums.Category
import com.signal.domain.repository.RecommendRepository

class RecommendRepositoryImpl(
    private val recommendDataSource: RecommendDataSource,
) : RecommendRepository {
    override suspend fun fetchRecommends(category: Category) = kotlin.runCatching {
        recommendDataSource.fetchRecommends(category = category).toEntity()
    }

}