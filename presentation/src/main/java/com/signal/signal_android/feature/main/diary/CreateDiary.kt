package com.signal.signal_android.feature.main.diary

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.signal.data.util.FileUtil
import com.signal.domain.enums.Emotion
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.button.SignalFilledButton
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.BodyLarge
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.textfield.SignalTextField
import com.signal.signal_android.designsystem.util.signalClickable
import com.signal.signal_android.feature.file.AttachmentSideEffect
import com.signal.signal_android.feature.file.AttachmentViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CreateDiary(
    moveToBack: () -> Unit,
    diaryViewModel: DiaryViewModel = koinViewModel(),
    attachmentViewModel: AttachmentViewModel = koinViewModel(),
) {
    val state by diaryViewModel.state.collectAsState()

    val fileState by attachmentViewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current

    var imagePreview: Uri? by remember { mutableStateOf(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        it?.run {
            imagePreview = it
            attachmentViewModel.setFile(
                FileUtil.toFile(
                    context = context,
                    uri = this,
                )
            )
        }
    }

    LaunchedEffect(Unit) {
        attachmentViewModel.sideEffect.collect {
            when (it) {
                is AttachmentSideEffect.Success -> {
                    diaryViewModel.createDiary(imageUrl = fileState.imageUrl)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        diaryViewModel.sideEffect.collect {
            when (it) {
                is DiarySideEffect.CreateDiarySuccess -> moveToBack()
                else -> {}
            }
        }
    }

    var date by remember { mutableStateOf(LocalDate.now()) }

    val sheetState = rememberModalBottomSheetState()
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
            SheetContent(
                date = date,
                onEmotionClick = {
                    diaryViewModel.setEmotion(emotion = it)
                    showBottomSheet = false
                },
            )
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
            painter = painterResource(
                id = emotionDrawable(state.emotion)
            ),
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
        DiaryField(title = state.title,
            content = state.content,
            onTitleChange = diaryViewModel::setTitle,
            onContentChange = diaryViewModel::setContent,
            imagePreview = imagePreview,
            launcher = launcher,
            onButtonClick = {
                if (imagePreview == null) {
                    diaryViewModel.createDiary()
                } else {
                    attachmentViewModel.uploadFile()
                }
                focusManager.clearFocus()
            })
    }
}


@Composable
private fun DiaryField(
    title: String,
    content: String,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    imagePreview: Uri?,
    launcher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    onButtonClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Body(
            text = stringResource(id = R.string.create_diary_title),
            color = SignalColor.Gray600,
        )
        Spacer(modifier = Modifier.height(6.dp))
        SignalTextField(
            value = title,
            onValueChange = onTitleChange,
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
            onValueChange = onContentChange,
            hint = "내용을 입력하세요",
            alignment = Alignment.Top,
            showLength = true,
            singleLine = false,
            maxLength = 100,
        )
        PostImage(
            uri = { imagePreview },
        ) {
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        Spacer(modifier = Modifier.weight(1f))
        SignalFilledButton(
            text = stringResource(id = R.string.my_page_secession_check),
            onClick = onButtonClick,
        )
        Spacer(modifier = Modifier.height(26.dp))
    }
}

@Composable
private fun PostImage(
    uri: () -> Uri?,
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
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = uri(),
            contentDescription = stringResource(id = R.string.diary_image),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun SheetContent(
    date: LocalDate,
    onEmotionClick: (Emotion) -> Unit,
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
                modifier = Modifier
                    .size(40.dp)
                    .signalClickable { onEmotionClick(Emotion.HAPPY) },
                painter = painterResource(id = R.drawable.ic_happy),
                contentDescription = stringResource(
                    id = R.string.emotion_happy
                ),
            )
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .signalClickable { onEmotionClick(Emotion.SOSO) },
                painter = painterResource(id = R.drawable.ic_soso),
                contentDescription = stringResource(
                    id = R.string.emotion_soso
                ),
            )
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .signalClickable { onEmotionClick(Emotion.DEPRESSION) },
                painter = painterResource(id = R.drawable.ic_depression),
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
                modifier = Modifier
                    .size(40.dp)
                    .signalClickable { onEmotionClick(Emotion.SADNESS) },
                painter = painterResource(id = R.drawable.ic_sadness),
                contentDescription = stringResource(
                    id = R.string.emotion_sadness
                ),
            )
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .signalClickable { onEmotionClick(Emotion.SURPRISED) },
                painter = painterResource(id = R.drawable.ic_surprised),
                contentDescription = stringResource(
                    id = R.string.emotion_surprised
                ),
            )
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .signalClickable { onEmotionClick(Emotion.DISCOMFORT) },
                painter = painterResource(id = R.drawable.ic_discomfort),
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
                modifier = Modifier
                    .size(40.dp)
                    .signalClickable { onEmotionClick(Emotion.PLEASED) },
                painter = painterResource(id = R.drawable.ic_pleased),
                contentDescription = stringResource(
                    id = R.string.emotion_pleased
                ),
            )
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .signalClickable { onEmotionClick(Emotion.ANGRY) },
                painter = painterResource(id = R.drawable.ic_angry),
                contentDescription = stringResource(
                    id = R.string.emotion_angry
                ),
            )
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .signalClickable { onEmotionClick(Emotion.AWKWARDNESS) },
                painter = painterResource(id = R.drawable.ic_awkwardness),
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
                modifier = Modifier
                    .size(40.dp)
                    .signalClickable { onEmotionClick(Emotion.SOBBING) },
                painter = painterResource(id = R.drawable.ic_sobbing),
                contentDescription = stringResource(
                    id = R.string.emotion_sobbing
                ),
            )
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .signalClickable { onEmotionClick(Emotion.ANNOYING) },
                painter = painterResource(id = R.drawable.ic_annoying),
                contentDescription = stringResource(
                    id = R.string.emotion_annoying
                ),
            )
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .signalClickable { onEmotionClick(Emotion.BOREDOM) },
                painter = painterResource(id = R.drawable.ic_boredom),
                contentDescription = stringResource(
                    id = R.string.emotion_boredom
                ),
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}