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
import androidx.compose.runtime.LaunchedEffect
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

@Composable
internal fun DiaryDetail(
    diaryId: Long,
    moveToBack: () -> Unit,
    diaryViewModel: DiaryViewModel = koinViewModel(),
) {
    val state by diaryViewModel.state.collectAsState()
    val details = state.diaryDetailsEntity

    LaunchedEffect(Unit) {
        diaryViewModel.setDiaryId(diaryId = diaryId)
        diaryViewModel.fetchDiaryDetails()
    }

    LaunchedEffect(Unit) {
        diaryViewModel.sideEffect.collect {
            when(it) {
                DiarySideEffect.DeleteSuccess -> moveToBack()
                else -> {}
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Header(title = stringResource(id = R.string.header_back),
            onLeadingClicked = moveToBack,
            trailingIcon = painterResource(id = R.drawable.ic_delete),
            onTrailingClicked = { diaryViewModel.deleteDiary() })
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                BodyLarge2(text = details.title)
                Body(
                    text = details.date,
                    color = SignalColor.Gray500,
                )
            }
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(
                    id = emotionDrawable(details.emotion)
                ),
                contentDescription = stringResource(id = R.string.diary_emotion_image),
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        if (details.image != null) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                model = details.image,
                contentDescription = stringResource(id = R.string.diary_details_image),
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        Body2(
            text = details.content,
            color = SignalColor.Gray700,
        )
    }
}