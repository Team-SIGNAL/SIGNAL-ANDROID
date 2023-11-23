package com.signal.data.datasource.recommend

import com.signal.data.api.RecommendApi
import com.signal.data.model.recommend.FetchRecommendsResponse
import com.signal.data.util.ExceptionHandler
import com.signal.domain.enums.Category

class RecommendDataSourceImpl(
    private val recommendApi: RecommendApi,
) : RecommendDataSource {
    override suspend fun fetchRecommends(category: Category) =
        ExceptionHandler<FetchRecommendsResponse>().httpRequest {
            recommendApi.fetchRecommends(category = category)
        }.sendRequest()
}