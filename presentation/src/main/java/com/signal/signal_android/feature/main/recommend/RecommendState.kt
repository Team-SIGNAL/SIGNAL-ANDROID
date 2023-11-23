package com.signal.signal_android.feature.main.recommend

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.signal.domain.entity.RecommendsEntity
import com.signal.domain.enums.Category

data class RecommendState(
    val recommends: SnapshotStateList<RecommendsEntity.Recommend>,
    val category: Category,
) {
    companion object {
        fun getDefaultState() = RecommendState(
            recommends = mutableStateListOf(),
            category = Category.MUSIC,
        )
    }
}