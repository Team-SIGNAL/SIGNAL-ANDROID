package com.signal.signal_android.feature.main.recommend

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.signal.domain.entity.RecommendDetailsEntity
import com.signal.domain.entity.RecommendsEntity
import com.signal.domain.enums.Category
import java.util.UUID

data class RecommendState(
    val recommends: SnapshotStateList<RecommendsEntity.Recommend>,
    val category: Category,
    val recommendId: UUID,
    val details: RecommendDetailsEntity,
) {
    companion object {
        fun getDefaultState() = RecommendState(
            recommends = mutableStateListOf(),
            category = Category.MUSIC,
            recommendId = UUID.randomUUID(),
            details = RecommendDetailsEntity(
                title = "",
                image = "",
                content = "",
                link = "",
                name = "",
                profile = "",
                createDate = "",
            ),
        )
    }
}