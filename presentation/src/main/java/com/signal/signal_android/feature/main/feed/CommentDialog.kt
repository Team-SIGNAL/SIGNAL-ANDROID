package com.signal.signal_android.feature.main.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.signal.domain.entity.PostCommentsEntity
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.button.SignalFilledButton
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.Body2
import com.signal.signal_android.designsystem.foundation.BodyLarge2
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.textfield.SignalTextField

@Composable
internal fun CommentDialog(
    state: FeedState,
    fetchPostComments: suspend () -> Unit,
) {
    LaunchedEffect(Unit) {
        fetchPostComments()
    }

    Box(contentAlignment = Alignment.BottomCenter) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .fillMaxHeight(0.8f),
        ) {
            Spacer(modifier = Modifier.height(14.dp))
            Divider(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 14.dp)
                    .size(
                        width = 48.dp,
                        height = 2.dp,
                    ),
                color = SignalColor.Gray400,
            )
            BodyLarge2(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.comment_dialog_comment),
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
            )
            Comments(commentEntities = state.comments)
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .background(SignalColor.White)
                .imePadding(),
        ) {
            Input(
                comment = { "comment" },
                onCommentChange = { },
                onClick = {},
            )
        }
    }
}

@Composable
private fun Input(
    comment: () -> String,
    onCommentChange: (String) -> Unit,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier.padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SignalTextField(
            modifier = Modifier.fillMaxWidth(0.8f),
            value = "",
            onValueChange = onCommentChange,
            hint = stringResource(id = R.string.comment_dialog_input_comment),
        )
        Spacer(modifier = Modifier.width(8.dp))
        SignalFilledButton(
            modifier = Modifier.weight(0.2f),
            text = stringResource(id = R.string.my_page_secession_check),
            onClick = onClick,
        )
    }
}

@Composable
private fun Comments(
    commentEntities: List<PostCommentsEntity.CommentEntity>,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(bottom = 40.dp),
    ) {
        items(commentEntities) {
            Comment(
                profileImageUrl = "",
                writer = it.writer,
                time = it.dateTime.toString(),
                content = it.content,
                onClick = {},
            )
        }
    }
}

@Composable
private fun Comment(
    profileImageUrl: String,
    writer: String,
    time: String,
    content: String,
    onClick: () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape),
            model = profileImageUrl,
            contentDescription = stringResource(id = R.string.my_page_profile_image),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Body2(
                    text = writer,
                    color = SignalColor.Primary300,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Body(
                    text = time,
                    color = SignalColor.Gray500,
                )
            }
            Body(
                text = content,
                color = SignalColor.Gray700,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = onClick,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_more),
                contentDescription = stringResource(id = R.string.feed_more),
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}
