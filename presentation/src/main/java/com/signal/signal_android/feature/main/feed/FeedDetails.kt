package com.signal.signal_android.feature.main.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.designsystem.component.SignalDialog
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.Body2
import com.signal.signal_android.designsystem.foundation.BodyLarge2
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.util.signalClickable
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun FeedDetails(
    feedId: Long,
    moveToFeedDetails: (feedId: Long) -> Unit,
    moveToBack: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    // TODO: 더미
    val title by remember {
        mutableStateOf("제목제목제목")
    }

    val feedImageUrl: String? by remember {
        mutableStateOf("https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Instagram_logo_2022.svg/640px-Instagram_logo_2022.svg.png")
    }

    val content by remember {
        mutableStateOf("몰라몰라몰라몰라몰람로람롬람롬람롬ㄹ\n몰라몰라몰라몰라몰람로람롬람롬람롬ㄹ\n몰라몰라몰라몰라몰람로람롬람롬람롬ㄹ\n몰라몰라몰라몰라몰람로람롬람롬람롬ㄹ\n몰라몰라몰라몰라몰람로람롬람롬람롬ㄹ\n")
    }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )

    var expanded by remember {
        mutableLongStateOf(-1)
    }

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            SignalDialog(
                title = stringResource(id = R.string.feed_delete_dialog_title),
                onCancelBtnClick = { showDialog = false },
                onCheckBtnClick = {},
            )
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            CommentDialog()
        },
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
        ),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Header(
                title = title,
                onClick = moveToBack,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
            ) {
                User(
                    profileImageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Instagram_logo_2022.svg/640px-Instagram_logo_2022.svg.png",
                    name = "승훈티비",
                    date = "2023-10-10",
                    onClick = { expanded = feedId },
                    expanded = expanded == feedId,
                    onDismissRequest = { expanded = -1 },
                )
                if (feedImageUrl != null) {
                    Spacer(modifier = Modifier.height(22.dp))
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        model = feedImageUrl,
                        contentDescription = stringResource(id = R.string.feed_details_image),
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Body2(
                    text = content,
                    color = SignalColor.Gray700,
                )
                Body(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 24.dp)
                        .signalClickable(
                            onClick = {
                                coroutineScope.launch {
                                    sheetState.show()
                                }
                            },
                        ),
                    text = stringResource(id = R.string.feed_details_check_comments),
                    color = SignalColor.Gray500,
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    color = SignalColor.Gray400,
                )
                BodyLarge2(
                    modifier = Modifier.padding(5.dp),
                    text = stringResource(id = R.string.feed_details_feed_list),
                )
                posts.forEach {
                    Post(
                        moveToFeedDetails = { moveToFeedDetails(it.feedId) },
                        imageUrl = it.imageUrl,
                        title = it.title,
                        date = it.date,
                        writer = it.writer,
                        onClick = { expanded = it.feedId },
                        expanded = expanded == it.feedId,
                        onDismissRequest = { expanded = -1 },
                        onEdit = {},
                        onDelete = { showDialog = true },
                        onReport = {},
                    )
                }
            }
        }
    }
}

@Composable
private fun User(
    profileImageUrl: String,
    name: String,
    date: String,
    onClick: () -> Unit,
    expanded: Boolean,
    onDismissRequest: () -> Unit,

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            model = profileImageUrl,
            contentDescription = stringResource(id = R.string.my_page_profile_image),
        )
        Spacer(modifier = Modifier.width(6.dp))
        Column {
            Body(text = name)
            Spacer(modifier = Modifier.height(4.dp))
            Body(
                text = date,
                color = SignalColor.Gray500,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            modifier = Modifier.size(18.dp),
            onClick = onClick,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_more),
                contentDescription = stringResource(id = R.string.feed_more),
            )
            FeedDropDownMenu(
                expanded = expanded,
                onDismissRequest = onDismissRequest,
                onEdit = { /*TODO*/ },
                onDelete = { /*TODO*/ },
            ) {
            }
        }
    }
}
