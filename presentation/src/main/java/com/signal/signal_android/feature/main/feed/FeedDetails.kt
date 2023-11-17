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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.util.signalClickable
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun FeedDetails(
    feedId: Long,
    moveToBack: () -> Unit,
    moveToCreatePost: (feedId: Long) -> Unit,
    feedViewModel: FeedViewModel = koinViewModel(),
) {
    LaunchedEffect(Unit) {
        with(feedViewModel) {
            setFeedId(feedId)
            fetchPostDetails()
        }

        feedViewModel.sideEffect.collect {
            when (it) {
                is FeedSideEffect.DeleteSuccess -> {
                    moveToBack()
                }

                else -> {

                }
            }
        }
    }

    val state by feedViewModel.state.collectAsState()
    val details = state.postDetailsEntity

    val coroutineScope = rememberCoroutineScope()

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
                onCheckBtnClick = {
                    showDialog = false
                    feedViewModel.deletePost()
                },
            )
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            CommentDialog(feedViewModel = feedViewModel)
        },
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            Header(
                title = details.title,
                onLeadingClicked = moveToBack,
            )
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
            ) {
                User(
                    profileImageUrl = details.profile,
                    name = details.writer,
                    date = details.date,
                    onClick = { expanded = feedId },
                    expanded = expanded == feedId,
                    onDismissRequest = { expanded = -1 },
                    isMine = details.isMine,
                    onEdit = { moveToCreatePost(feedId) },
                    onDelete = { showDialog = true }
                )
                if (details.image != null) {
                    Spacer(modifier = Modifier.height(22.dp))
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        model = details.image,
                        contentDescription = stringResource(id = R.string.feed_details_image),
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Body2(
                    text = details.content,
                    color = SignalColor.Gray700,
                )
                Body(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 24.dp)
                        .signalClickable(
                            onClick = {
                                coroutineScope.launch {
                                    feedViewModel.fetchPostComments()
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
            }
        }
    }
}

@Composable
private fun User(
    profileImageUrl: String?,
    name: String,
    date: String,
    onClick: () -> Unit,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    isMine: Boolean,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
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
            model = profileImageUrl ?: R.drawable.ic_profile_image,
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
                isMine = isMine,
                onEdit = onEdit,
                onDelete = onDelete,
                onReport = { /*TODO*/ },
            )
        }
    }
}
