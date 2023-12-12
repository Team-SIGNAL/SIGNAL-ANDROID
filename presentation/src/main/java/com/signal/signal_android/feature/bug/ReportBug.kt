package com.signal.signal_android.feature.bug

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.signal.data.util.FileUtil
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.button.SignalFilledButton
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.designsystem.textfield.SignalTextField
import com.signal.signal_android.feature.file.AttachmentViewModel
import com.signal.signal_android.feature.main.feed.PostImage
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ReportBug(
    moveToBack: () -> Unit,
    bugViewModel: BugViewModel = koinViewModel(),
    attachmentViewModel: AttachmentViewModel = koinViewModel(),
) {
    val state by bugViewModel.state.collectAsState()
    val attachmentState by attachmentViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        bugViewModel.sideEffect.collect {
            when (it) {
                is BugSideEffect.Success -> moveToBack()
            }
        }
    }

    val context = LocalContext.current

    var imagePreview: Uri? by remember { mutableStateOf(null) }
    val imageUrl by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        it?.run {
            imagePreview = it
            with(attachmentViewModel) {
                setFile(
                    FileUtil.toFile(
                        context = context,
                        uri = this@run,
                    )
                )
                uploadFile()
            }
        }
    }

    var reason by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 24.dp,
            )
    ) {
        Header(
            title = stringResource(id = R.string.report_bug_header_title),
            onLeadingClicked = moveToBack,
        )
        Spacer(modifier = Modifier.height(12.dp))
        SignalTextField(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .padding(bottom = 28.dp),
            value = state.content,
            onValueChange = bugViewModel::setContent,
            showLength = true,
            maxLength = 1000,
            hint = stringResource(id = R.string.report_bug_hint_reason),
            alignment = Alignment.Top,
        )
        PostImage(
            imagePreview = { imagePreview },
            imageUrl = { imageUrl },
        ) {
            focusManager.clearFocus()
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        Spacer(modifier = Modifier.weight(1f))
        SignalFilledButton(
            text = stringResource(id = R.string.my_page_secession_check),
            onClick = {
                bugViewModel.setImage(image = attachmentState.imageUrl)
                bugViewModel.reportBug()
            },
            enabled = state.content.isNotEmpty(),
        )
    }
}