package com.signal.signal_android.feature.main.feed

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material.DropdownMenu
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.signal.domain.PostsEntity
import com.signal.domain.enums.Tag
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.button.SignalOutlinedButton
import com.signal.signal_android.designsystem.component.SignalDialog
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.BodyStrong
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.foundation.SubTitle
import com.signal.signal_android.designsystem.util.signalClickable
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun Feed(
    moveToFeedDetails: (feedId: Long) -> Unit,
    moveToCreatePost: () -> Unit,
    moveToReport: () -> Unit,
    feedViewModel: FeedViewModel = koinViewModel(),
) {
    val state by feedViewModel.state.collectAsState()

    var expanded by remember { mutableLongStateOf(-1) }

    var showDialog by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (state.isPostsEmpty) 1f else 0f,
        label = "",
    )

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            SignalDialog(
                title = stringResource(id = R.string.feed_delete_dialog_title),
                onCancelBtnClick = { showDialog = false },
                onCheckBtnClick = {},
            )
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
                    id = when (state.tag) {
                        Tag.GENERAL -> R.string.feed_all
                        Tag.NOTIFICATION -> R.string.feed_notice
                    },
                ),
                onClick = feedViewModel::setTag,
            )
            Spacer(modifier = Modifier.height(18.dp))
            Box {
                Posts(
                    moveToFeedDetails = moveToFeedDetails,
                    moveToReport = moveToReport,
                    posts = state.posts,
                    showDropDown = { expanded = it },
                    expanded = expanded,
                    onDismissRequest = { expanded = -1 },
                    onDelete = { showDialog = true },
                )
                Column(
                    modifier = Modifier.alpha(alpha),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    SubTitle(text = stringResource(id = R.string.feed_posts_is_empty))
                    Body(
                        modifier = Modifier.signalClickable(onClick = moveToCreatePost),
                        text = stringResource(id = R.string.feed_posts_add),
                        color = SignalColor.Primary100,
                    )
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier.padding(16.dp),
            onClick = moveToCreatePost,
            backgroundColor = SignalColor.Primary100,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = stringResource(id = R.string.feed_post),
                tint = SignalColor.White,
            )
        }
    }
}

@Composable
private fun Posts(
    moveToFeedDetails: (feedId: Long) -> Unit,
    moveToReport: () -> Unit,
    showDropDown: (feedId: Long) -> Unit,
    posts: List<PostsEntity.PostEntity>,
    onDismissRequest: () -> Unit,
    expanded: Long,
    onDelete: () -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(posts) {
            Post(
                moveToFeedDetails = { moveToFeedDetails(it.id) },
                imageUrl = it.img,
                title = it.title,
                date = it.date,
                writer = it.user,
                onClick = { showDropDown(it.id) },
                expanded = expanded == it.id,
                onDismissRequest = onDismissRequest,
                onEdit = {},
                onDelete = onDelete,
                onReport = moveToReport,
            )
        }
    }
}

@Composable
internal fun Post(
    moveToFeedDetails: () -> Unit,
    imageUrl: String,
    title: String,
    date: String,
    writer: String,
    onClick: () -> Unit,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onReport: () -> Unit,
) {
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp),
            )
            .clip(RoundedCornerShape(8.dp))
            .signalClickable(
                rippleEnabled = true,
                onClick = moveToFeedDetails,
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
                    onClick = onClick,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_more),
                        contentDescription = stringResource(id = R.string.header_back),
                    )
                    FeedDropDownMenu(
                        expanded = expanded,
                        onDismissRequest = onDismissRequest,
                        isMine = false,
                        onEdit = onEdit,
                        onDelete = onDelete,
                        onReport = onReport,
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

@Composable
internal fun FeedDropDownMenu(
    expanded: Boolean,
    isMine: Boolean = false,
    onDismissRequest: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onReport: () -> Unit,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
    ) {
        if (isMine) {
            DropdownMenuItem(
                text = {
                    Body(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = stringResource(id = R.string.feed_edit),
                    )
                },
                onClick = onEdit,
            )
        }
        if (isMine) {
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                text = {
                    Body(
                        modifier = Modifier.align(Alignment.End),
                        text = stringResource(id = R.string.feed_delete),
                        color = SignalColor.Error,
                    )
                },
                onClick = onDelete,
            )
        }
        DropdownMenuItem(
            text = {
                Body(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(id = R.string.feed_report),
                    color = SignalColor.Error,
                )
            },
            onClick = onReport,
        )
    }
}
