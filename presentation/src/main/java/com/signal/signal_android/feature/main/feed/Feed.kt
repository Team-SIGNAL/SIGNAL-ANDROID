package com.signal.signal_android.feature.main.feed

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.signal.domain.entity.PostsEntity
import com.signal.domain.enums.Tag
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.designsystem.component.SignalDialog
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.Body2
import com.signal.signal_android.designsystem.foundation.BodyStrong
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.foundation.SubTitle
import com.signal.signal_android.designsystem.util.signalClickable
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun Feed(
    moveToFeedDetails: (feedId: Long) -> Unit,
    moveToCreatePost: (feedId: Long) -> Unit,
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

    LaunchedEffect(Unit) {
        if (state.isPostsEmpty) {
            feedViewModel.fetchPosts()
        }
    }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            SignalDialog(
                title = stringResource(id = R.string.feed_delete_dialog_title),
                onCancelBtnClick = { showDialog = false },
                onCheckBtnClick = {},
            )
        }
    }

    var filterExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            Header(
                title = stringResource(id = R.string.feed),
                leadingIcon = null,
                trailingIcon = painterResource(id = R.drawable.ic_search),
                onTrailingClicked = {},
            )
            Filter(
                expanded = { filterExpanded },
                currentTag = { state.tag },
                onSelect = {
                    feedViewModel.setTag(it)
                    filterExpanded = false
                },
                onClick = { filterExpanded = true },
            )
            Box {
                Posts(
                    moveToFeedDetails = moveToFeedDetails,
                    moveToReport = moveToReport,
                    posts = state.posts,
                    showDropDown = {
                        feedViewModel.setFeedId(it)
                        expanded = it
                    },
                    expanded = expanded,
                    onDismissRequest = { expanded = -1 },
                    onDelete = feedViewModel::deletePost,
                    onEdit = { moveToCreatePost(state.feedId) },
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
                        text = stringResource(id = R.string.feed_posts_is_empty),
                    )
                    Body(
                        modifier = Modifier.signalClickable(
                            onClick = { moveToCreatePost(-1) },
                            enabled = state.isPostsEmpty
                        ),
                        text = stringResource(id = R.string.feed_posts_add),
                        color = SignalColor.Primary100,
                    )
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier.padding(16.dp),
            onClick = { moveToCreatePost(-1) },
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
private fun Filter(
    expanded: () -> Boolean,
    currentTag: () -> Tag,
    onSelect: (Tag) -> Unit,
    onClick: () -> Unit,
) {
    val tag = currentTag()

    Row(
        modifier = Modifier.padding(
            vertical = 10.dp,
        )
    ) {
        IconButton(onClick = onClick) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = stringResource(id = R.string.feed_filter),
                )
                Body2(
                    text = tag.value,
                    color = SignalColor.Primary100,
                )
            }
            DropdownMenu(
                expanded = expanded(),
                onDismissRequest = { /*TODO*/ },
            ) {
                DropdownMenuItem(
                    text = {
                        Body2(
                            text = Tag.GENERAL.value,
                            color = if (tag == Tag.GENERAL) SignalColor.Primary100
                            else SignalColor.Gray600,
                        )
                    },
                    onClick = { onSelect(Tag.GENERAL) },
                )
                DropdownMenuItem(
                    text = {
                        Body2(
                            text = Tag.NOTIFICATION.value,
                            color = if (tag == Tag.NOTIFICATION) SignalColor.Primary100
                            else SignalColor.Gray600,
                        )
                    },
                    onClick = { onSelect(Tag.NOTIFICATION) },
                )
                DropdownMenuItem(
                    text = {
                        Body2(
                            text = Tag.HOSPITAL.value,
                            color = if (tag == Tag.HOSPITAL) SignalColor.Primary100
                            else SignalColor.Gray600,
                        )
                    },
                    onClick = { onSelect(Tag.HOSPITAL) },
                )
            }
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
    onEdit: () -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(posts) {
            Post(
                moveToFeedDetails = { moveToFeedDetails(it.id) },
                imageUrl = it.image,
                title = it.title,
                date = it.date,
                name = it.name,
                isMine = it.isMine,
                onClick = { showDropDown(it.id) },
                expanded = expanded == it.id,
                onDismissRequest = onDismissRequest,
                onEdit = onEdit,
                onDelete = onDelete,
                onReport = moveToReport,
            )
        }
    }
}

@Composable
internal fun Post(
    moveToFeedDetails: () -> Unit,
    imageUrl: String?,
    title: String,
    date: String,
    name: String,
    isMine: Boolean,
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
        imageUrl?.run {
            AsyncImage(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(4.dp)),
                model = this,
                contentDescription = stringResource(id = R.string.feed_image),
                contentScale = ContentScale.Crop,
            )
        }
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
                        isMine = isMine,
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
                    text = name,
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
    isMine: Boolean,
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
