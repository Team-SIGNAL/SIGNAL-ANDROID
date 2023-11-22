package com.signal.signal_android.feature.main.recommend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.signal.domain.enums.RecommendType
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.feature.main.diary.DiaryItemList
import java.util.UUID

@Composable
internal fun Recommends(
    moveToRecommendDetails: (feedId: UUID) -> Unit,
    moveToBack: () -> Unit,
    recommendType: String?,
) {
    val headerTitle = recommendType?.run { RecommendType.valueOf(this).value }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Header(
            title = headerTitle ?: "",
            onLeadingClicked = moveToBack,
        )
        Recommends(
            moveToRecommendDetails = moveToRecommendDetails,
            recommends = {
                listOf(
                    Recommend(
                        id = UUID.randomUUID(),
                        imageUrl = null,
                        title = "title",
                        writer = "writer",
                        link = null,
                    )
                )
            },
        )
    }
}

data class Recommend(
    val id: UUID,
    val imageUrl: String?,
    val title: String,
    val writer: String,
    val link: String?,
)

@Composable
internal fun Recommends(
    moveToRecommendDetails: (feedId: UUID) -> Unit,
    recommends: () -> List<Recommend>,
) {
    LazyColumn {
        items(recommends()) {
            DiaryItemList(
                moveToDiaryDetails = { moveToRecommendDetails(it.id) },
                title = it.title,
                content = it.writer,
                imageUrl = it.imageUrl,
            )
        }
    }
}