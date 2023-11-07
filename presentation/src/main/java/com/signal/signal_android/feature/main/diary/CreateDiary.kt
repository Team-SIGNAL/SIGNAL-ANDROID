package com.signal.signal_android.feature.main.diary

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.button.SignalFilledButton
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.BodyLarge
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.textfield.SignalTextField
import com.signal.signal_android.designsystem.util.signalClickable
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CreateDiary() {
    var date by remember { mutableStateOf(LocalDate.now()) }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var onTitleChange by remember { mutableStateOf("") }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            contentColor = SignalColor.White,
            containerColor = SignalColor.White,
        ) {
            // Sheet content
            SheetContent(date)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
                vertical = 30.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Image(
            modifier = Modifier.signalClickable { showBottomSheet = true },
            painter = painterResource(id = R.drawable.ic_happy),
            contentDescription = stringResource(
                id = R.string.create_diary_emotion_image
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        BodyLarge(
            text = date.toString(),
            color = SignalColor.Gray500,
        )
        Spacer(modifier = Modifier.height(15.dp))
        DiaryField(
            title = title,
            content = content,
        )
    }
}


@Composable
private fun DiaryField(
    title: String,
    content: String,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Body(
            text = stringResource(id = R.string.create_diary_title),
            color = SignalColor.Gray600,
        )
        Spacer(modifier = Modifier.height(6.dp))
        SignalTextField(
            value = title,
            onValueChange = { title },
            hint = "제목을 입력하세요",
            showLength = true,
            maxLength = 20,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Body(
            text = stringResource(id = R.string.create_diary_content),
            color = SignalColor.Gray600,
        )
        Spacer(modifier = Modifier.height(6.dp))
        SignalTextField(
            modifier = Modifier.fillMaxHeight(0.5f),
            value = content,
            onValueChange = { content },
            hint = "내용을 입력하세요",
            alignment = Alignment.Top,
            showLength = true,
            singleLine = false,
            maxLength = 100,
        )
        PostImage(onClick = {})
        Spacer(modifier = Modifier.weight(1f))
        SignalFilledButton(
            text = stringResource(id = R.string.my_page_secession_check),
            onClick = { /* TODO */ },
        )
        Spacer(modifier = Modifier.height(26.dp))
    }
}

@Composable
private fun PostImage(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(vertical = 28.dp)
            .clip(RoundedCornerShape(8.dp))
            .size(88.dp)
            .background(SignalColor.Gray300)
            .signalClickable(
                onClick = onClick,
                rippleEnabled = true,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(28.dp),
            painter = painterResource(id = R.drawable.ic_gallery),
            contentDescription = stringResource(id = R.string.create_post_image),
            tint = SignalColor.Gray500,
        )
    }
}

@Composable
private fun SheetContent(
    date: LocalDate,
) {
    Column(
        modifier = Modifier.background(color = SignalColor.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(9.dp))
        Body(
            text = date.toString(),
            color = SignalColor.Gray500,
        )
        Body(
            text = stringResource(id = R.string.create_diary_select_emotion),
            color = SignalColor.Gray600,
        )
        Spacer(modifier = Modifier.height(34.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = Emotion.HAPPY.emotionImage),
                contentDescription = stringResource(
                    id = R.string.emotion_happy
                ),
            )
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = Emotion.SOSO.emotionImage),
                contentDescription = stringResource(
                    id = R.string.emotion_soso
                ),
            )
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = Emotion.DEPRESSION.emotionImage),
                contentDescription = stringResource(
                    id = R.string.emotion_depression
                ),
            )
        }
        Spacer(modifier = Modifier.height(28.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = Emotion.SADNESS.emotionImage),
                contentDescription = stringResource(
                    id = R.string.emotion_sadness
                ),
            )
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = Emotion.SURPRISED.emotionImage),
                contentDescription = stringResource(
                    id = R.string.emotion_surprised
                ),
            )
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = Emotion.DISCOMFORT.emotionImage),
                contentDescription = stringResource(
                    id = R.string.emotion_discomfort
                ),
            )
        }
        Spacer(modifier = Modifier.height(28.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = Emotion.PLEASED.emotionImage),
                contentDescription = stringResource(
                    id = R.string.emotion_pleased
                ),
            )
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = Emotion.ANGRY.emotionImage),
                contentDescription = stringResource(
                    id = R.string.emotion_engry
                ),
            )
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = Emotion.AWKWARDNESS.emotionImage),
                contentDescription = stringResource(
                    id = R.string.emotion_awkwardness
                ),
            )
        }
        Spacer(modifier = Modifier.height(28.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = Emotion.SOBBING.emotionImage),
                contentDescription = stringResource(
                    id = R.string.emotion_sobbing
                ),
            )
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = Emotion.ANNOYING.emotionImage),
                contentDescription = stringResource(
                    id = R.string.emotion_annoying
                ),
            )
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = Emotion.BOREDOM.emotionImage),
                contentDescription = stringResource(
                    id = R.string.emotion_boredom
                ),
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}