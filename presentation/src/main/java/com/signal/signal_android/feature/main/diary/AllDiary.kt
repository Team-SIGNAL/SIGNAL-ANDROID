package com.signal.signal_android.feature.main.diary

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.BodyStrong
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.util.signalClickable

@Composable
internal fun AllDiary(
    moveToDiaryDetails: (diaryId: Long) -> Unit,
    moveToBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Header(
            title = stringResource(id = R.string.diary_all_diary),
            onClick = moveToBack,
        )
        Row(modifier = Modifier.fillMaxSize()) {
            Diaries(
                moveToDiaryDetails = moveToDiaryDetails,
                diaries = diaries,
            )
        }
    }
}

@Composable
private fun Diaries(
    moveToDiaryDetails: (diaryId: Long) -> Unit,
    diaries: List<_Diaries>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(diaries) {
            DiaryItemList(
                moveToDiaryDetails = { moveToDiaryDetails(it.diaryId) },
                title = it.title,
                content = it.content,
                imageUrl = it.imageUrl,
                emotion = it.emotion,
            )
        }
    }
}

@Composable
private fun DiaryItemList(
    moveToDiaryDetails: () -> Unit,
    title: String,
    content: String,
    imageUrl: String?,
    emotion: Int,
) {
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(8.dp),
            )
            .background(
                color = SignalColor.White,
                shape = RoundedCornerShape(8.dp),
            )
            .clip(shape = RoundedCornerShape(8.dp))
            .signalClickable(
                rippleEnabled = true,
                onClick = moveToDiaryDetails,
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (imageUrl != null) {
            AsyncImage(
                modifier = Modifier
                    .size(58.dp)
                    .clip(RoundedCornerShape(4.dp)),
                model = imageUrl,
                contentDescription = stringResource(id = R.string.diary_image),
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Column(verticalArrangement = Arrangement.Center) {
            BodyStrong(text = title)
            Body(
                text = content,
                color = SignalColor.Gray500,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            Box(modifier = Modifier.size(40.dp)) {
                Image(
                    painterResource(id = emotion),
                    contentDescription = stringResource(id = R.string.diary_emotion_image),
                )
            }
        }
    }
}