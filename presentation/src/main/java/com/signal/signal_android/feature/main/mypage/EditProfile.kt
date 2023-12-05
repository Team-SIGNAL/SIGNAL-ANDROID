package com.signal.signal_android.feature.main.mypage

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.signal.data.util.FileUtil
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.button.SignalFilledButton
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.designsystem.foundation.BodyStrong
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.util.signalClickable
import com.signal.signal_android.feature.file.AttachmentSideEffect
import com.signal.signal_android.feature.file.AttachmentViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun EditProfile(
    moveToBack: () -> Unit,
    myPageViewModel: MyPageViewModel = koinViewModel(),
    attachmentViewModel: AttachmentViewModel = koinViewModel(),
) {
    val state by myPageViewModel.state.collectAsState()
    val fileState by attachmentViewModel.state.collectAsState()
    var imagePreview: Uri? by remember { mutableStateOf(null) }
    val focusManager = LocalFocusManager.current

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
                    myPageViewModel.editProfile(image = fileState.imageUrl)
                }

                is AttachmentSideEffect.Failure -> {

                }
            }
        }
    }

    LaunchedEffect(Unit) {
        myPageViewModel.sideEffect.collect {
            when (it) {
                is MyPageSideEffect.EditProfileSuccess -> moveToBack()

                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header(
            title = stringResource(id = R.string.edit_profile_image),
            onLeadingClicked = moveToBack,
        )
        Spacer(modifier = Modifier.height(100.dp))
        Box(
            modifier = Modifier
                .padding(vertical = 28.dp)
                .clip(RoundedCornerShape(8.dp))
                .size(160.dp),
            contentAlignment = Alignment.Center,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape),
                model = state.profile ?: R.drawable.ic_profile_image,
                contentDescription = stringResource(id = R.string.my_page_profile_image),
                contentScale = ContentScale.Crop,
            )
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                model = imagePreview,
                contentDescription = stringResource(id = R.string.my_page_profile_image),
                contentScale = ContentScale.Crop,
            )
        }
        BodyStrong(
            modifier = Modifier.signalClickable {
                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
            text = stringResource(id = R.string.change_profile),
            color = SignalColor.Primary100,
        )
        Spacer(modifier = Modifier.weight(1f))
        SignalFilledButton(
            text = stringResource(id = R.string.change),
            onClick = {
                attachmentViewModel.uploadFile()
                focusManager.clearFocus()
            },
        )
        Spacer(modifier = Modifier.height(26.dp))
    }
}