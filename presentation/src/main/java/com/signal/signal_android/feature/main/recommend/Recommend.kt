package com.signal.signal_android.feature.main.recommend

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.signal.domain.enums.Category
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.designsystem.component.Indicator
import com.signal.signal_android.designsystem.foundation.Body2
import com.signal.signal_android.designsystem.foundation.BodyLarge
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.foundation.Title
import com.signal.signal_android.designsystem.util.signalClickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val images = listOf(
    R.drawable.bg_recommend_music,
    R.drawable.bg_recommend_exercise,
    R.drawable.bg_recommend_video,
    R.drawable.bg_recommend_hospital,
)

private val strings = listOf(
    R.string.recommend_music,
    R.string.recommend_exercise,
    R.string.recommend_video,
    R.string.recommend_hospital,
)

@Composable
internal fun Recommend(
    moveToRecommends: (recommendType: String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 32.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header(
            title = stringResource(id = R.string.recommend),
            leadingIcon = null,
        )
        Trends(moveToRecommends = moveToRecommends)
        Categories(moveToRecommends = moveToRecommends)
    }
}

@Composable
private fun ColumnScope.Categories(
    moveToRecommends: (recommendType: String) -> Unit,
) {
    BodyLarge(
        modifier = Modifier
            .padding(
                top = 16.dp,
                bottom = 4.dp,
            )
            .align(Alignment.Start),
        text = stringResource(id = R.string.recommend_categories),
    )
    LazyRow(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
        itemsIndexed(images) { index, item ->
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .signalClickable(
                        rippleEnabled = true,
                        onClick = {
                            moveToRecommends(Category.values()[index].toString())
                        },
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxHeight(0.4f)
                        .width(120.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .shadow(
                            elevation = 80.dp,
                            shape = RoundedCornerShape(6.dp),
                        ),
                    painter = painterResource(id = item),
                    contentDescription = stringResource(id = R.string.recommend_image),
                    contentScale = ContentScale.Crop,
                )
                Body2(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = stringResource(id = strings[index]),
                )
            }
        }
    }
    Spacer(modifier = Modifier.weight(1f))
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ColumnScope.Trends(
    moveToRecommends: (recommendType: String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState { images.size }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (true) {
                delay(5000)
                pagerState.animateScrollToPage(if (pagerState.currentPage != 3) pagerState.currentPage + 1 else 0)
            }
        }
    }

    BodyLarge(
        modifier = Modifier
            .padding(
                top = 28.dp,
                bottom = 12.dp,
            )
            .align(Alignment.Start),
        text = stringResource(id = R.string.recommend_trend),
    )
    Box(contentAlignment = Alignment.BottomCenter) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .signalClickable(
                    rippleEnabled = true,
                    onClick = {
                        moveToRecommends(Category.values()[pagerState.currentPage].toString())
                    },
                )
                .fillMaxHeight(0.25f),
            state = pagerState,
        ) {
            Box(
                contentAlignment = when (it) {
                    0 -> Alignment.TopStart
                    1 -> Alignment.BottomEnd
                    else -> Alignment.BottomStart
                },
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = images[it]),
                    contentDescription = stringResource(id = R.string.recommend_image),
                    contentScale = ContentScale.Crop,
                )
                Title(
                    modifier = Modifier.padding(12.dp),
                    text = stringResource(id = strings[it]),
                    color = SignalColor.White,
                )
            }
        }
        Row(
            modifier = Modifier.padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(images.size) {
                Indicator(isEnabled = it == pagerState.currentPage)
            }
        }
    }
}
