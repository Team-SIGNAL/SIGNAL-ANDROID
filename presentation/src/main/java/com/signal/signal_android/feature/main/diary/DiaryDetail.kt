package com.signal.signal_android.feature.main.diary

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.signal.domain.enums.Emotion.*
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.designsystem.component.SignalDialog
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.Body2
import com.signal.signal_android.designsystem.foundation.BodyLarge2
import com.signal.signal_android.designsystem.foundation.SignalColor
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun DiaryDetail(
    diaryId: Long,
    moveToBack: () -> Unit,
    diaryViewModel: DiaryViewModel = koinViewModel(),
) {
    val state by diaryViewModel.state.collectAsState()

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
            onLeadingClicked = moveToBack,
            trailingIcon = painterResource(id = R.drawable.ic_delete),
            onTrailingClicked = {/*TODO delete*/}
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                BodyLarge2(text = state.title)
                Body(
                    text = state.date,
                    color = SignalColor.Gray500,
                )
            }
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(
                    id = when (state.emotion) {
                        HAPPY -> R.drawable.ic_happy
                        SOSO -> R.drawable.ic_soso
                        DEPRESSION -> R.drawable.ic_depression
                        SADNESS -> R.drawable.ic_sadness
                        SURPRISED -> R.drawable.ic_surprised
                        DISCOMFORT -> R.drawable.ic_discomfort
                        PLEASED -> R.drawable.ic_pleased
                        ANGRY -> R.drawable.ic_angry
                        AWKWARDNESS -> R.drawable.ic_awkwardness
                        SOBBING -> R.drawable.ic_sobbing
                        ANNOYING -> R.drawable.ic_annoying
                        BOREDOM -> R.drawable.ic_boredom
                    }
                ),
                contentDescription = stringResource(id = R.string.diary_emotion_image),
            )/*DiaryDropDown(
                onEdit = { *//*TODO*//* },
                onDelete = { showDialog = true },
            )*/
        }
        Spacer(modifier = Modifier.height(18.dp))
        if (state.image != null) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                model = state.image,
                contentDescription = stringResource(id = R.string.diary_details_image),
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        Body2(
            text = state.content,
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
            modifier = Modifier.size(18.dp),
            painter = painterResource(id = R.drawable.ic_more),
            contentDescription = stringResource(
                id = R.string.feed_more
            ),
        )
        DropdownMenu(
            expanded = isDropDownMenuExpanded,
            onDismissRequest = { isDropDownMenuExpanded = false },
        ) {
            DropdownMenuItem(onClick = onEdit) {
                Text(text = stringResource(id = R.string.feed_edit))
            }
            DropdownMenuItem(onClick = onDelete) {
                Text(
                    text = stringResource(id = R.string.feed_delete),
                    color = SignalColor.Error,
                )
            }
        }
    }
}
