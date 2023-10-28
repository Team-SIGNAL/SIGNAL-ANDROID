package com.signal.signal_android.feature.main.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.button.SignalOutlinedButton
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.BodyStrong
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.foundation.SubTitle
import com.signal.signal_android.designsystem.util.signalClickable

// TODO 더미
internal data class _Post(
    val feedId: Long,
    val imageUrl: String,
    val title: String,
    val date: String,
    val writer: String,
)

// TODO 더미
private val posts = listOf(
    _Post(
        feedId = 1,
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Instagram_logo_2022.svg/640px-Instagram_logo_2022.svg.png",
        title = "인스타그램",
        date = "2023-10-28",
        writer = "정승훈",
    ),
)

// TODO 더미
private enum class Type {
    ALL, NOTICE, RECOMMEND,
}

@Composable
internal fun Feed(
    moveToFeedDetails: (feedId: Long) -> Unit,
) {
    // TODO 뷰모델에서 구현
    var type by remember { mutableStateOf(Type.ALL) }

    val onClick: () -> Unit = {
        type = when (type) {
            Type.ALL -> Type.NOTICE
            Type.NOTICE -> Type.RECOMMEND
            else -> Type.ALL
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            SubTitle(text = stringResource(id = R.string.feed))
            Spacer(modifier = Modifier.height(8.dp))
            // TODO 버튼 디자인 시스템 변경
            SignalOutlinedButton(
                modifier = Modifier.size(
                    width = 58.dp,
                    height = 48.dp,
                ),
                text = stringResource(
                    id = when (type) {
                        Type.ALL -> R.string.feed_all
                        Type.NOTICE -> R.string.feed_notice
                        else -> R.string.feed_recommend
                    },
                ),
                onClick = onClick,
            )
            Spacer(modifier = Modifier.height(18.dp))
            Posts(
                moveToFeedDetails = moveToFeedDetails,
                posts = posts,
            )
        }
        FloatingActionButton(
            modifier = Modifier.padding(32.dp),
            onClick = { /*TODO*/ },
            backgroundColor = SignalColor.Primary100,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = stringResource(id = R.string.edit),
                tint = SignalColor.White,
            )
        }
    }
}

@Composable
private fun Posts(
    moveToFeedDetails: (feedId: Long) -> Unit,
    posts: List<_Post>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(posts) {
            Post(
                onClick = { moveToFeedDetails(it.feedId) },
                imageUrl = it.imageUrl,
                title = it.title,
                date = it.date,
                writer = it.writer,
            )
        }
    }
}

@Composable
private fun Post(
    onClick: () -> Unit,
    imageUrl: String,
    title: String,
    date: String,
    writer: String,
) {
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp),
            )
            .clip(RoundedCornerShape(8.dp))
            .signalClickable(
                rippleEnabled = true,
                onClick = onClick,
            )
            .background(
                color = SignalColor.White,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier.size(48.dp),
            model = imageUrl,
            contentDescription = stringResource(id = R.string.feed_image),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                BodyStrong(text = title)
                IconButton(
                    modifier = Modifier.size(16.dp),
                    onClick = {},
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_more),
                        contentDescription = stringResource(id = R.string.more),
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Body(
                    text = writer,
                    color = SignalColor.Gray500,
                )
                Body(
                    text = date,
                    color = SignalColor.Gray500,
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}
