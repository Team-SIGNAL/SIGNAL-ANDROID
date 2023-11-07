package com.signal.signal_android.feature.main.diary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.designsystem.component.SignalDialog
import com.signal.signal_android.designsystem.foundation.Body2
import com.signal.signal_android.designsystem.foundation.BodyLarge2
import com.signal.signal_android.designsystem.foundation.SignalColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun DiaryDetail(
    diaryId: Long,
    moveToBack: () -> Unit,
) {
    // TODO: 더미
    val title by remember {
        mutableStateOf("제목제목제목")
    }

    val diaryImageUrl: String? by remember {
        mutableStateOf("https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Instagram_logo_2022.svg/640px-Instagram_logo_2022.svg.png")
    }

    val content by remember {
        mutableStateOf("몰라몰라몰라몰라몰람로람롬람롬람롬ㄹ\n몰라몰라몰라몰라몰람로람롬람롬람롬ㄹ\n몰라몰라몰라몰라몰람로람롬람롬람롬ㄹ\n몰라몰라몰라몰라몰람로람롬람롬람롬ㄹ\n몰라몰라몰라몰라몰람로람롬람롬람롬ㄹ\n")
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Header(
            title = stringResource(id = R.string.header_back),
            onClick = moveToBack,
        )
        Row {
            BodyLarge2(text = title)
            DiaryDropDown(
                onEdit = { /*TODO*/ },
                onDelete = {/*TODO*/ },
            )
        }
        if (diaryImageUrl != null) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                model = diaryImageUrl,
                contentDescription = stringResource(id = R.string.diary_details_image),
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Body2(
            text = content,
            color = SignalColor.Gray700,
        )
    }
}

@Composable
private fun DiaryDropDown(
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }

    IconButton(onClick = { isDropDownMenuExpanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_more),
            contentDescription = stringResource(
                id = R.string.feed_more
            ),
        )
    }

    DropdownMenu(
        expanded = isDropDownMenuExpanded,
        onDismissRequest = { isDropDownMenuExpanded = false },
    ) {
        DropdownMenuItem(onClick = onEdit) {
            Text(text = stringResource(id = R.string.feed_edit))
        }
        DropdownMenuItem(onClick = onDelete) {
            Text(text = stringResource(id = R.string.feed_delete))
        }
    }
}
