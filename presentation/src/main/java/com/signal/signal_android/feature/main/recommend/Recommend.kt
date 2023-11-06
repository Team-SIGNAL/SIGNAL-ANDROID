package com.signal.signal_android.feature.main.recommend

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.component.Indicator
import com.signal.signal_android.designsystem.foundation.Body2
import com.signal.signal_android.designsystem.foundation.BodyLarge
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.foundation.SubTitle
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
internal fun Recommend() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 30.dp,
                bottom = 32.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header()
        Trends()
        Categories()
        MyContent {
        }
    }
}

@Composable
private fun MyContent(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(8.dp),
                clip = true,
            )
            .background(
                color = SignalColor.White,
                shape = RoundedCornerShape(8.dp),
            )
            .clip(RoundedCornerShape(8.dp))
            .signalClickable(
                rippleEnabled = true,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Body2(text = stringResource(id = R.string.recommend_my))
    }
}

@Composable
private fun ColumnScope.Categories() {
    BodyLarge(
        modifier = Modifier
            .padding(
                top = 16.dp,
                bottom = 4.dp,
            )
            .align(Alignment.Start),
        text = stringResource(id = R.string.recommend_categories),
    )
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        itemsIndexed(images) { index, item ->
            Column(
                modifier = Modifier.signalClickable(
                    rippleEnabled = true,
                    onClick = {},
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
                    modifier = Modifier.padding(top = 4.dp),
                    text = stringResource(id = strings[index]),
                )
                Body2(
                    modifier = Modifier.padding(top = 2.dp),
                    text = "뭐시깽이",
                    color = SignalColor.Primary100,
                )
            }
        }
    }
    Spacer(modifier = Modifier.weight(1f))
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SubTitle(text = stringResource(id = R.string.recommend))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = {},
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(id = R.string.recommend_search),
                )
            }
            IconButton(
                modifier = Modifier.size(22.dp),
                onClick = {},
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = stringResource(id = R.string.feed_post),
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ColumnScope.Trends() {
    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState { 4 }

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
