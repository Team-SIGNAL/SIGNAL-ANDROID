package com.signal.signal_android.feature.main.recommend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.signal.domain.entity.RecommendsEntity
import com.signal.domain.enums.Category
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.feature.main.diary.DiaryItemList
import org.koin.androidx.compose.koinViewModel
import java.util.UUID

@Composable
internal fun Recommends(
    moveToRecommendDetails: (feedId: UUID) -> Unit,
    moveToBack: () -> Unit,
    category: Category,
    recommendViewModel: RecommendViewModel = koinViewModel(),
) {
    val state by recommendViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        with(recommendViewModel) {
            setCategory(category = category)
            fetchRecommends()
        }
    }

    val headerTitle = category.run {
        when (category) {
            Category.MUSIC -> stringResource(id = R.string.recommend_music)
            Category.SPORT -> stringResource(id = R.string.recommend_exercise)
            Category.VIDEO -> stringResource(id = R.string.recommend_video)
            Category.HOBBY -> stringResource(id = R.string.recommend_hospital)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Header(
            title = headerTitle,
            onLeadingClicked = moveToBack,
        )
        Recommends(
            moveToRecommendDetails = moveToRecommendDetails,
            recommends = { state.recommends },
        )
    }
}

@Composable
internal fun Recommends(
    moveToRecommendDetails: (feedId: UUID) -> Unit,
    recommends: () -> SnapshotStateList<RecommendsEntity.Recommend>,
) {
    LazyColumn {
        items(recommends()) {
            DiaryItemList(
                moveToDiaryDetails = { moveToRecommendDetails(it.id) },
                title = it.title,
                content = it.content,
                imageUrl = it.image,
            )
        }
    }
}