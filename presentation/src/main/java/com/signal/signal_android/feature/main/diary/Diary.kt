package com.signal.signal_android.feature.main.diary

import android.widget.CalendarView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.signal.domain.entity.DayDiaryEntity
import com.signal.domain.enums.Emotion
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.Body2
import com.signal.signal_android.designsystem.foundation.BodyStrong
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.foundation.SubTitle
import com.signal.signal_android.designsystem.util.signalClickable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun Diary(
    moveToCreateDiary: () -> Unit,
    moveToDiaryDetails: (diaryId: Long) -> Unit,
    moveToAllDiary: () -> Unit,
    diaryViewModel: DiaryViewModel,
) {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)
    val date = Date()

    val state by diaryViewModel.state.collectAsState()
    var yearState by remember { mutableStateOf(formatter.format(date).split("-").first()) }
    var monthState by remember { mutableStateOf(formatter.format(date).split("-")[1]) }
    var dayState by remember { mutableStateOf(formatter.format(date).split("-").last()) }

    LaunchedEffect(Unit) {
        diaryViewModel.fetchDayDiary()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 30.dp),
        ) {
            SubTitle(text = stringResource(id = R.string.diary))
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    AndroidView(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = SignalColor.Primary100,
                                shape = RoundedCornerShape(8.dp),
                            )
                            .fillMaxWidth(),
                        factory = { CalendarView(it) },
                    ) { calendarView ->
                        val selectedDate = "${yearState}-${monthState}-${dayState}"
                        calendarView.date = formatter.parse(selectedDate)!!.time


                        calendarView.setOnDateChangeListener { _, year, month, day ->
                            yearState = year.toString()
                            monthState = (month + 1).toString()
                            dayState = day.toString()
                        }
                    }
                }

                stickyHeader {
                    DiaryHeader(
                        month = monthState,
                        day = dayState,
                        moveToAllDiary = moveToAllDiary,
                    )
                }
            }
            Diaries(
                moveToDiaryDetails = moveToDiaryDetails,
                diaries = state.dayDiaries,
            )
        }
        FloatingActionButton(
            modifier = Modifier.padding(16.dp),
            onClick = { moveToCreateDiary() },
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
private fun DiaryHeader(
    month: String,
    day: String,
    moveToAllDiary: () -> Unit,
) {
    Spacer(modifier = Modifier.height(26.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        BodyStrong(
            text = month + "월 " + day + "일",
            color = SignalColor.Black,
        )
        Body2(
            modifier = Modifier.signalClickable { moveToAllDiary() },
            text = stringResource(id = R.string.diary_all_diary),
        )
    }
}

@Composable
private fun Diaries(
    moveToDiaryDetails: (diaryId: Long) -> Unit,
    diaries: List<DayDiaryEntity.DayDiaryEntity>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(diaries) {
            DiaryItems(
                moveToDiaryDetails = { moveToDiaryDetails(it.id) },
                title = it.title,
                content = it.content,
                imageUrl = it.image,
                emotion = it.emotion,
            )
        }
    }
}

@Composable
internal fun DiaryItems(
    moveToDiaryDetails: () -> Unit,
    title: String,
    content: String,
    imageUrl: String?,
    emotion: Emotion,
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
            Box(
                modifier = Modifier.size(40.dp),
            ) {
                Image(
                    painterResource(
                        id = when (emotion) {
                            Emotion.HAPPY -> R.drawable.ic_happy
                            Emotion.SOSO -> R.drawable.ic_soso
                            Emotion.DEPRESSION -> R.drawable.ic_depression
                            Emotion.SADNESS -> R.drawable.ic_sadness
                            Emotion.SURPRISED -> R.drawable.ic_surprised
                            Emotion.DISCOMFORT -> R.drawable.ic_discomfort
                            Emotion.PLEASED -> R.drawable.ic_pleased
                            Emotion.ANGRY -> R.drawable.ic_angry
                            Emotion.AWKWARDNESS -> R.drawable.ic_awkwardness
                            Emotion.SOBBING -> R.drawable.ic_sobbing
                            Emotion.ANNOYING -> R.drawable.ic_annoying
                            Emotion.BOREDOM -> R.drawable.ic_boredom
                        }
                    ),
                    contentDescription = stringResource(id = R.string.diary_emotion_image),
                )
            }
        }
    }
}