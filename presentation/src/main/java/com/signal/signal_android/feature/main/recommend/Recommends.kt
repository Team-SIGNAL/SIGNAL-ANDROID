package com.signal.signal_android.feature.main.recommend

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.signal.domain.entity.RecommendsEntity
import com.signal.domain.enums.Category
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.designsystem.foundation.SubTitle
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

    val context = LocalContext.current

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
            Category.HOBBY -> stringResource(id = R.string.recommend_hobby)
        }
    }

    val alpha by animateFloatAsState(
        targetValue = if (state.recommends.isEmpty()) 1f else 0f,
        label = "",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Header(
            title = headerTitle,
            onLeadingClicked = moveToBack,
        )
        Box {
            Recommends(
                moveToRecommendDetails = moveToRecommendDetails,
                recommends = { state.recommends },
                moveToLink = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                    context.startActivity(intent)
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .alpha(alpha),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(0.3f)
                        .padding(bottom = 8.dp),
                    painter = painterResource(id = R.drawable.ic_surprised),
                    contentDescription = stringResource(id = R.string.emotion_surprised),
                )
                SubTitle(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = stringResource(id = R.string.recommend_empty),
                )
            }
        }
    }
}

@Composable
internal fun Recommends(
    moveToRecommendDetails: (feedId: UUID) -> Unit,
    recommends: () -> SnapshotStateList<RecommendsEntity.Recommend>,
    moveToLink: (link: String) -> Unit,
) {
    LazyColumn(contentPadding = PaddingValues(vertical = 16.dp)) {
        items(recommends()) {
            DiaryItemList(
                moveToDiaryDetails = { moveToRecommendDetails(it.id) },
                title = it.title,
                content = it.content,
                imageUrl = it.image,
                iconEnabled = true,
                onIconClicked = {
                    it.link?.run(moveToLink)
                },
            )
        }
    }
}